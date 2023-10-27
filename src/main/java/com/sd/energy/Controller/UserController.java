package com.sd.energy.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.tools.json.JSONUtil;
import com.sd.energy.Dto.*;
import com.sd.energy.Model.User;
import com.sd.energy.Model.UserProfile;
import com.sd.energy.Service.UserService;
import com.sd.energy.Utility.JsonUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/createNewUserAndProfile")
    public ResponseEntity<String> createNewUserAndProfile(@Valid @RequestBody NewUserDto requestBody)
    {
        boolean status = userService.processNewUserRequest(requestBody);
        if (!status)
            return ResponseEntity.badRequest().body("User and profile creation has failed!");

        return ResponseEntity.ok("User and profile created succesfully!");
    }

    @GetMapping("/getAllUserProfiles")
    public ResponseEntity<String> getAllUserProfiles() throws JsonProcessingException {
        List<UserProfile> userProfiles = userService.findAllUserProfiles();
        return JsonUtility.createJsonResponse(userProfiles);
    }

    @PostMapping("/getUserProfileForId")
    public ResponseEntity<String> getUserProfileForId(@RequestBody UserIdDto requestBody) throws JsonProcessingException {
        UserProfile userProfile = userService.processGetUserProfileForIdRequest(requestBody);
        return JsonUtility.createJsonResponse(userProfile);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UserIdDto requestBody)
    {
        boolean status = userService.processDeleteUserRequest(requestBody);
        if (!status)
            return ResponseEntity.badRequest().body("User deletion has failed!");

        return ResponseEntity.ok("User successfully deleted!");
    }

    @PostMapping("/updateUserProfile")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UserUpdateDto requestBody) throws JsonProcessingException {
        UserProfile savedUserProfile = userService.processUpdateUserProfileRequest(requestBody);
        if (savedUserProfile == null)
            return ResponseEntity.badRequest().body("User profile update failed!");

        return JsonUtility.createJsonResponse(savedUserProfile);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDto> authenticateCredentials(@Valid @RequestBody AuthDto authRequest)
    {
        AuthResponseDto authResponse = userService.processAuthenticateCredentialsRequest(authRequest);
        if (authResponse == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(authResponse);
    }

}
