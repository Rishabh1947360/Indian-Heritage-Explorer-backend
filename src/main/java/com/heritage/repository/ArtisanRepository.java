package com.heritage.repository;

import com.heritage.entity.Artisan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArtisanRepository extends JpaRepository<Artisan, Long> {
    List<Artisan> findByStateOrderByCreatedAtDesc(String state);
    List<Artisan> findByCraftContainingIgnoreCaseOrderByCreatedAtDesc(String craft);
    List<Artisan> findByStateAndCraftContainingIgnoreCase(String state, String craft);
}
