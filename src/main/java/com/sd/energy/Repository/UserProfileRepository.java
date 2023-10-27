package com.sd.energy.Repository;

import com.sd.energy.Model.User;
import com.sd.energy.Model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID>
{
    List<UserProfile> findAll();
    Optional<UserProfile> findById(@Param("id") UUID id);
    Optional<UserProfile> findByUser(@Param("user") User user);

    Optional<UserProfile> findByName(@Param("name") String name);
}
