package com.heritage.controller;

import com.heritage.dto.ApiResponse;
import com.heritage.dto.HeritageSiteDto;
import com.heritage.service.HeritageSiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heritage-sites")
@RequiredArgsConstructor
public class HeritageSiteController {

    private final HeritageSiteService siteService;

    // GET /api/heritage-sites?state=Rajasthan
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllSites(
            @RequestParam(required = false) String state) {
        return ResponseEntity.ok(siteService.getAllSites(state));
    }

    // GET /api/heritage-sites/states/list
    @GetMapping("/states/list")
    public ResponseEntity<ApiResponse<List<String>>> getStates() {
        return ResponseEntity.ok(siteService.getStates());
    }

    // GET /api/heritage-sites/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HeritageSiteDto>> getSiteById(@PathVariable String id) {
        ApiResponse<HeritageSiteDto> response = siteService.getSiteById(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
