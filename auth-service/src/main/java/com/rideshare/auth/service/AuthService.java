package com.rideshare.auth.service;

import com.rideshare.auth.dto.AuthResponse;
import com.rideshare.auth.dto.LoginRequest;
import com.rideshare.auth.dto.RegisterRequest;
import com.rideshare.auth.dto.UserProfileResponse;
import com.rideshare.auth.entity.Role;
import com.rideshare.auth.entity.UserAccount;
import com.rideshare.auth.exception.ApiException;
import com.rideshare.auth.repository.UserAccountRepository;
import com.rideshare.auth.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserAccountRepository userAccountRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == Role.ADMIN) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Admin registration is not allowed");
        }
        if (userAccountRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ApiException(HttpStatus.CONFLICT, "Email is already registered");
        }
        if (userAccountRepository.existsByPhone(request.getPhone())) {
            throw new ApiException(HttpStatus.CONFLICT, "Phone number is already registered");
        }
        UserAccount user = new UserAccount();
        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPhone(request.getPhone().trim());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userAccountRepository.save(user);
        return toAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        UserAccount user = userAccountRepository.findByEmailIgnoreCase(request.getEmail().trim())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        if (!user.isActive() || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return toAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public java.util.List<UserProfileResponse> listUsers() {
        return userAccountRepository.findAll().stream()
                .map(this::toProfile)
                .toList();
    }

    @Transactional
    public UserProfileResponse updateActive(Long id, boolean active) {
        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getRole() == Role.ADMIN && !active) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Admin accounts cannot be deactivated");
        }
        user.setActive(active);
        return toProfile(userAccountRepository.save(user));
    }

    public UserProfileResponse toProfile(UserAccount user) {
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getPhone(),
                user.getFullName(),
                user.getRole(),
                user.isActive()
        );
    }

    private AuthResponse toAuthResponse(UserAccount user) {
        String token = jwtService.generateToken(user);
        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                jwtService.getExpirationMs()
        );
    }
}
