package com.heritage.service;

import com.heritage.dto.ApiResponse;
import com.heritage.dto.HeritageSiteDto;
import com.heritage.entity.HeritageSite;
import com.heritage.repository.HeritageSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeritageSiteService {

    private final HeritageSiteRepository siteRepository;

    // GET /api/heritage-sites  (optionally filtered by state)
    public ApiResponse<?> getAllSites(String state) {
        List<HeritageSite> sites;

        if (state != null && !state.isBlank()) {
            sites = siteRepository.findByStateOrderByName(state);
            return ApiResponse.ok(sites.stream().map(HeritageSiteDto::from).toList());
        }

        // Return grouped by state
        sites = siteRepository.findAll();
        Map<String, List<HeritageSiteDto>> sitesByState = sites.stream()
                .map(HeritageSiteDto::from)
                .collect(Collectors.groupingBy(HeritageSiteDto::getState));

        return ApiResponse.ok(sitesByState);
    }

    // GET /api/heritage-sites/:id
    public ApiResponse<HeritageSiteDto> getSiteById(String id) {
        return siteRepository.findById(id)
                .map(site -> ApiResponse.ok(HeritageSiteDto.from(site)))
                .orElse(ApiResponse.error("Heritage site not found."));
    }

    // GET /api/heritage-sites/states/list
    public ApiResponse<List<String>> getStates() {
        return ApiResponse.ok(siteRepository.findAllDistinctStates());
    }
}
