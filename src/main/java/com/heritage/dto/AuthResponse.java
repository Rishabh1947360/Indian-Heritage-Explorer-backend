package com.heritage.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private boolean success;
    private String message;
    private String token;
    private UserDto user;
}
