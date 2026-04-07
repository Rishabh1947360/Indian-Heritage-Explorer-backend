package com.heritage.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "heritage_sites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeritageSite {

    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 200)
    private String location;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "year_built", length = 50)
    private String yearBuilt;

    @Column(name = "entry_fee_indian", length = 50)
    private String entryFeeIndian;

    @Column(name = "entry_fee_foreign", length = 50)
    private String entryFeeForeign;

    @Column(name = "opening_hours", length = 100)
    private String openingHours;

    @Column(name = "closed_on", length = 100)
    private String closedOn;

    @Column(name = "best_time", length = 100)
    private String bestTime;

    @Column(name = "daily_visitors", length = 100)
    private String dailyVisitors;

    @Column(nullable = false)
    private boolean unesco = false;

    @Column(precision = 3)
    private Double rating;

    private Integer reviews;

    @Column(name = "virtual_tour_url", columnDefinition = "TEXT")
    private String virtualTourUrl;

    @Column(name = "virtual_tour_video_url", columnDefinition = "TEXT")
    private String virtualTourVideoUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
