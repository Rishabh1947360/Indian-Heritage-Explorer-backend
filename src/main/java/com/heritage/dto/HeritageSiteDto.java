package com.heritage.dto;

import com.heritage.entity.HeritageSite;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HeritageSiteDto {
    private String id;
    private String name;
    private String state;
    private String location;
    private String image;
    private String description;
    private String yearBuilt;
    private EntryFee entryFee;
    private String openingHours;
    private String closedOn;
    private String bestTime;
    private String dailyVisitors;
    private boolean unesco;
    private Double rating;
    private Integer reviews;
    private String virtualTourUrl;
    private String virtualTourVideoUrl;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class EntryFee {
        private String indian;
        private String foreign;
    }

    public static HeritageSiteDto from(HeritageSite s) {
        return HeritageSiteDto.builder()
            .id(s.getId())
            .name(s.getName())
            .state(s.getState())
            .location(s.getLocation())
            .image(s.getImage())
            .description(s.getDescription())
            .yearBuilt(s.getYearBuilt())
            .entryFee(new EntryFee(s.getEntryFeeIndian(), s.getEntryFeeForeign()))
            .openingHours(s.getOpeningHours())
            .closedOn(s.getClosedOn())
            .bestTime(s.getBestTime())
            .dailyVisitors(s.getDailyVisitors())
            .unesco(s.isUnesco())
            .rating(s.getRating())
            .reviews(s.getReviews())
            .virtualTourUrl(s.getVirtualTourUrl())
            .virtualTourVideoUrl(s.getVirtualTourVideoUrl())
            .build();
    }
}
