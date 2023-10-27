package com.sd.energy.Repository;

import com.sd.energy.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>
{
    List<User> findAll();
    Optional<User> findById(@Param("id") UUID id);
    Optional<User> findByUsername(@Param("username") String username);

}
