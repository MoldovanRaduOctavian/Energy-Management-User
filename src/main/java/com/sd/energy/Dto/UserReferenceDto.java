package com.sd.energy.Dto;

import java.util.UUID;

public class UserReferenceDto {

    private UUID userReferenceId;

    public UserReferenceDto(UUID userReferenceId)
    {
        this.userReferenceId = userReferenceId;
    }

    public UUID getUserReferenceId() {
        return userReferenceId;
    }

    public void setUserReferenceId(UUID userReferenceId) {
        this.userReferenceId = userReferenceId;
    }
}
