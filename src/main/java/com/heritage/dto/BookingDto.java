package com.heritage.dto;

import com.heritage.entity.Booking;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BookingDto {
    private Long id;
    private String site;
    private String location;
    private String state;
    private String siteId;
    private String image;
    private LocalDate date;
    private Integer visitors;
    private BigDecimal amount;
    private String status;

    public static BookingDto from(Booking b) {
        return BookingDto.builder()
            .id(b.getId())
            .site(b.getSiteName())
            .location(b.getLocation())
            .state(b.getState())
            .siteId(b.getSiteId())
            .image(b.getImage())
            .date(b.getVisitDate())
            .visitors(b.getVisitors())
            .amount(b.getAmount())
            .status(b.getStatus().name())
            .build();
    }
}
