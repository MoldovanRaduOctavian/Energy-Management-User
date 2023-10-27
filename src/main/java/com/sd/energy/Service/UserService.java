package com.sd.energy.Service;

import com.sd.energy.Builder.UserBuilder;
import com.sd.energy.Builder.UserProfileBuilder;
import com.sd.energy.Dto.*;
import com.sd.energy.Model.User;
import com.sd.energy.Model.UserProfile;
import com.sd.energy.Repository.UserRepository;
import com.sd.energy.Repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<User> findAllUsers()
    {
        return userRepository.findAll();
    }

    public List<UserProfile> findAllUserProfiles() { return userProfileRepository.findAll(); }

    public User createUser(User user)
    {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser.isPresent())
            return null;
        return userRepository.save(user);
    }

    public void deleteUser(User user) { userRepository.delete(user); }

    public Optional<UserProfile> findUserProfileById(UUID id) { return userProfileRepository.findById(id); }

    public UserProfile createUserProfile(UserProfile userProfile)
    {
        return userProfileRepository.save(userProfile);
    }

    public void deleteUserProfile(UserProfile userProfile) { userProfileRepository.delete(userProfile); }

    public boolean processNewUserRequest(NewUserDto requestBody)
    {
        if (requestBody == null)
            return false;

        User foundUser = userRepository.findByUsername(requestBody.getUsername()).orElse(null);
        if (foundUser != null)
            return false;

        User user = new UserBuilder().withUsername(requestBody.getUsername())
                .withPassword(requestBody.getPassword())
                .withAdmin(requestBody.isAdmin()).build();

        UserProfile userProfile = new UserProfileBuilder().withName(requestBody.getName())
                .withDescription(requestBody.getDescription())
                .withUser(user).build();

        user.setUserProfile(userProfile);

        User savedUser = userRepository.save(user);
        if (savedUser == null)
            return false;

        UserProfile savedUserProfile = userProfileRepository.save(userProfile);
        if (savedUserProfile == null)
            return false;

        // System.out.println("Saved user profile ID: " + savedUserProfile.getId());
        String createUserReferenceUrl = "http://localhost:8082/api/devices/createUserReference";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserReferenceDto> request = new HttpEntity<UserReferenceDto>(
            new UserReferenceDto(savedUserProfile.getId())
        );

        ResponseEntity<String> response = restTemplate
                .exchange(createUserReferenceUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK)
            return false;

        return true;
    }

    public AuthResponseDto processAuthenticateCredentialsRequest(AuthDto authRequest)
    {
        User user = userRepository.findByUsername(authRequest.getUsername()).orElse(null);
        if (user == null)
            return null;

        UserProfile userProfile = userProfileRepository.findByUser(user).orElse(null);
        if (userProfile == null)
            return null;

        AuthResponseDto authResponse = new AuthResponseDto(user.getId(), userProfile.getId(), user.isAdmin());

        if (user.getPassword().equals(authRequest.getPassword()))
            return authResponse;

        return null;
    }

    public boolean processDeleteUserRequest(UserIdDto requestBody)
    {
        UUID id = requestBody.getUserProfileId();
        UserProfile userProfile = this.findUserProfileById(id).orElse(null);

        if (userProfile == null)
            return false;

        User user = userProfile.getUser();
        this.deleteUser(user);
        this.deleteUserProfile(userProfile);

        String deleteUserReferenceUrl = "http://localhost:8082/api/devices/deleteUserReference";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<UserReferenceDto> request = new HttpEntity<UserReferenceDto>(
                new UserReferenceDto(userProfile.getId())
        );

        ResponseEntity<String> response = restTemplate
                .exchange(deleteUserReferenceUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK)
            return false;

        return true;
    }

    public UserProfile processUpdateUserProfileRequest(UserUpdateDto requestBody)
    {
        UUID id = requestBody.getUserProfileId();
        UserProfile userProfile = this.findUserProfileById(id).orElse(null);

        if (userProfile == null)
            return null;

        String name = requestBody.getName();
        String description = requestBody.getDescription();

        userProfile.setName(name);
        userProfile.setDescription(description);

        UserProfile savedUserProfile = this.createUserProfile(userProfile);

        return savedUserProfile;
    }

    public UserProfile processGetUserProfileForIdRequest(UserIdDto requestBody)
    {
        if (requestBody.getUserProfileId() == null)
            return null;
        UserProfile userProfile = this.findUserProfileById(requestBody.getUserProfileId()).orElse(null);
        return userProfile;
    }
}
