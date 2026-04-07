package com.heritage.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SignupRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank @Email(message = "Valid email is required")
    private String email;

    @NotBlank @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String phone;
    private String state;
}
