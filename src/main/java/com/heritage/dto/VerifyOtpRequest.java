package com.heritage.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class VerifyOtpRequest {
    @NotBlank @Email
    private String email;

    @NotBlank
    private String otp;
}
