package com.heritage.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ArtisanRequest {
    @NotBlank private String name;
    @NotBlank private String craft;
    @NotBlank private String state;
    @NotBlank private String location;
    @NotBlank private String experience;
    @NotBlank private String phone;
    @NotBlank @Email private String email;
    private String specialty;
}
