package com.sd.energy.Dto;

import java.util.UUID;

public class AuthResponseDto
{
    private UUID userId;
    private UUID userProfileId;

    private boolean admin;

    public AuthResponseDto(UUID userId, UUID userProfileId, boolean admin)
    {
        this.userId = userId;
        this.userProfileId = userProfileId;
        this.admin = admin;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(UUID userProfileId) {
        this.userProfileId = userProfileId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
