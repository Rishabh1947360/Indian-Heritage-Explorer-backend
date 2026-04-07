package com.heritage.controller;

import com.heritage.dto.ApiResponse;
import com.heritage.dto.ArtisanRequest;
import com.heritage.entity.Artisan;
import com.heritage.service.ArtisanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artisans")
@RequiredArgsConstructor
public class ArtisanController {

    private final ArtisanService artisanService;

    // GET /api/artisans?state=Rajasthan&craft=Pottery
    @GetMapping
    public ResponseEntity<ApiResponse<List<Artisan>>> getAllArtisans(
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String craft) {
        return ResponseEntity.ok(artisanService.getAllArtisans(state, craft));
    }

    // POST /api/artisans
    @PostMapping
    public ResponseEntity<ApiResponse<Artisan>> registerArtisan(
            @Valid @RequestBody ArtisanRequest request) {
        return ResponseEntity.status(201).body(artisanService.registerArtisan(request));
    }
}
