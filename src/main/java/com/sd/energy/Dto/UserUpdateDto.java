package com.sd.energy.Dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class UserUpdateDto {

    private UUID userProfileId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public UUID getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UUID userProfileId) {
        this.userProfileId = userProfileId;
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
}
