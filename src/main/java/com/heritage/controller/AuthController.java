package com.heritage.controller;

import com.heritage.dto.*;
import com.heritage.entity.User;
import com.heritage.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody SignupRequest request) {
        ApiResponse<Void> response = authService.signup(request);
        int status = response.isSuccess() ? 201 : 409;
        return ResponseEntity.status(status).body(response);
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequest request) {
        ApiResponse<Void> response = authService.login(request);
        int status = response.isSuccess() ? 200 : 401;
        return ResponseEntity.status(status).body(response);
    }

    // POST /api/auth/verify-otp
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        ApiResponse<AuthResponse> response = authService.verifyOtp(request);
        int status = response.isSuccess() ? 200 : 400;
        return ResponseEntity.status(status).body(response);
    }

    // GET /api/auth/me
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(authService.getMe(user.getId()));
    }
}
