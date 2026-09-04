package com.rideshare.auth.dto;

import com.rideshare.auth.entity.Role;

public class UserProfileResponse {

    private Long id;
    private String email;
    private String phone;
    private String fullName;
    private Role role;
    private boolean active;

    public UserProfileResponse(Long id, String email, String phone, String fullName, Role role, boolean active) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return fullName;
    }

    public Role getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }
}
