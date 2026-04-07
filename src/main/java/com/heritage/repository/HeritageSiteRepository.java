package com.heritage.repository;

import com.heritage.entity.HeritageSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface HeritageSiteRepository extends JpaRepository<HeritageSite, String> {
    List<HeritageSite> findByStateOrderByName(String state);

    @Query("SELECT DISTINCT h.state FROM HeritageSite h ORDER BY h.state")
    List<String> findAllDistinctStates();
}
