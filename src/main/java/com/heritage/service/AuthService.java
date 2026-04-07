package com.heritage.service;

import com.heritage.dto.*;
import com.heritage.entity.Otp;
import com.heritage.entity.User;
import com.heritage.repository.OtpRepository;
import com.heritage.repository.UserRepository;
import com.heritage.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    // ── SIGNUP ──────────────────────────────────────────────
    @Transactional
    public ApiResponse<Void> signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail().toLowerCase())) {
            return ApiResponse.error("An account with this email already exists.");
        }

        User user = User.builder()
                .name(request.getName().trim())
                .email(request.getEmail().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .state(request.getState())
                .build();

        userRepository.save(user);

        // Send welcome email (async, non-blocking)
        emailService.sendWelcomeEmail(user.getEmail(), user.getName());

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Account created successfully! You can now login.")
                .build();
    }

    // ── LOGIN (Step 1 — validate credentials, send OTP) ─────
    @Transactional
    public ApiResponse<Void> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase())
                .orElse(null);

        if (user == null) {
            return ApiResponse.error("No account found with this email.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ApiResponse.error("Incorrect password. Please try again.");
        }

        // Delete old OTPs for this email
        otpRepository.deleteByEmail(user.getEmail());

        // Generate and save new OTP
        String otp = generateOtp();
        System.out.println("🔥 OTP: " + otp);
        Otp otpEntity = Otp.builder()
                .email(user.getEmail())
                .otp(otp)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .used(false)
                .build();
        otpRepository.save(otpEntity);

        // Send OTP email (async)
        emailService.sendOtpEmail(user.getEmail(), user.getName(), otp);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("OTP sent to " + user.getEmail() + ". Please check your inbox.")
                .build();
    }

    // ── VERIFY OTP (Step 2 — verify OTP, return JWT) ────────
    @Transactional
    public ApiResponse<AuthResponse> verifyOtp(VerifyOtpRequest request) {
        Otp otpRecord = otpRepository
                .findTopByEmailAndOtpAndUsedFalseOrderByCreatedAtDesc(
                        request.getEmail().toLowerCase(), request.getOtp())
                .orElse(null);

        if (otpRecord == null) {
            return ApiResponse.error("Invalid OTP. Please try again.");
        }

        if (LocalDateTime.now().isAfter(otpRecord.getExpiresAt())) {
            return ApiResponse.error("OTP has expired. Please login again.");
        }

        // Mark OTP as used
        otpRecord.setUsed(true);
        otpRepository.save(otpRecord);

        // Get user
        User user = userRepository.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getName());

        AuthResponse authResponse = AuthResponse.builder()
                .success(true)
                .message("Login successful!")
                .token(token)
                .user(UserDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .state(user.getState())
                        .build())
                .build();

        return ApiResponse.ok("Login successful!", authResponse);
    }

    // ── GET CURRENT USER ─────────────────────────────────────
    public ApiResponse<UserDto> getMe(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .state(user.getState())
                .build();

        return ApiResponse.ok(userDto);
    }

    // ── HELPERS ──────────────────────────────────────────────
    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
