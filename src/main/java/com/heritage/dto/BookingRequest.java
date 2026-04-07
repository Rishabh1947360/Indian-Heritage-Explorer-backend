package com.heritage.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class BookingRequest {
    @NotBlank(message = "site_id is required")
    private String siteId;

    @NotBlank
    private String siteName;

    @NotBlank
    private String location;

    @NotBlank
    private String state;

    private String image;

    @NotNull(message = "visit_date is required")
    private LocalDate visitDate;

    @Min(1) @Max(20)
    private Integer visitors = 1;

    private BigDecimal amount = BigDecimal.ZERO;
}
