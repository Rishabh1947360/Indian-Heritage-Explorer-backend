package com.heritage.service;

import com.heritage.dto.ApiResponse;
import com.heritage.dto.ArtisanRequest;
import com.heritage.entity.Artisan;
import com.heritage.repository.ArtisanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtisanService {

    private final ArtisanRepository artisanRepository;

    private static final String DEFAULT_IMAGE =
            "https://images.unsplash.com/photo-1650726583448-dda0065f2f11?w=400";

    // GET /api/artisans?state=Rajasthan&craft=Pottery
    public ApiResponse<List<Artisan>> getAllArtisans(String state, String craft) {
        List<Artisan> artisans;

        if (state != null && craft != null) {
            artisans = artisanRepository.findByStateAndCraftContainingIgnoreCase(state, craft);
        } else if (state != null) {
            artisans = artisanRepository.findByStateOrderByCreatedAtDesc(state);
        } else if (craft != null) {
            artisans = artisanRepository.findByCraftContainingIgnoreCaseOrderByCreatedAtDesc(craft);
        } else {
            artisans = artisanRepository.findAll();
        }

        return ApiResponse.ok(artisans);
    }

    // POST /api/artisans
    @Transactional
    public ApiResponse<Artisan> registerArtisan(ArtisanRequest request) {
        Artisan artisan = Artisan.builder()
                .name(request.getName().trim())
                .craft(request.getCraft())
                .state(request.getState())
                .location(request.getLocation().trim())
                .experience(request.getExperience().trim())
                .phone(request.getPhone().trim())
                .email(request.getEmail().toLowerCase())
                .image(DEFAULT_IMAGE)
                .specialty(request.getSpecialty() != null ? request.getSpecialty() : "")
                .build();

        Artisan saved = artisanRepository.save(artisan);
        return ApiResponse.ok("Artisan registered successfully!", saved);
    }
}
