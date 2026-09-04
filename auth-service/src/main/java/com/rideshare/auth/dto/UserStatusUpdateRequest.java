package com.rideshare.auth.dto;

import jakarta.validation.constraints.NotNull;

public class UserStatusUpdateRequest {

    @NotNull
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
