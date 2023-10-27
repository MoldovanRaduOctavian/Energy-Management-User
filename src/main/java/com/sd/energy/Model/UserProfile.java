package com.sd.energy.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sd.energy.Builder.UserProfileBuilder;
import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Entity
@Table(name = "user_profiles")
public class UserProfile
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    public UserProfile()
    {

    }

    public UserProfile(UserProfileBuilder builder)
    {
        this.name = builder.name;
        this.description = builder.description;
        this.user = builder.user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
