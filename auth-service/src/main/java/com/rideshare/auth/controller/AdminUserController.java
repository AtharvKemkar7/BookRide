package com.rideshare.auth.controller;

import com.rideshare.auth.dto.UserProfileResponse;
import com.rideshare.auth.dto.UserStatusUpdateRequest;
import com.rideshare.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
public class AdminUserController {

    private final AuthService authService;

    public AdminUserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public List<UserProfileResponse> list() {
        return authService.listUsers();
    }

    @PatchMapping("/{id}/status")
    public UserProfileResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UserStatusUpdateRequest request) {
        return authService.updateActive(id, request.getActive());
    }
}
