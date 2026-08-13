package com.trexova.repository;

import com.trexova.model.Retreat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RetreatRepository extends JpaRepository<Retreat, Long> {

    List<Retreat> findByActiveTrue();

    List<Retreat> findByTypeAndActiveTrue(String type);

    // Custom query for multi-filter search
    @Query("SELECT r FROM Retreat r WHERE r.active = true " +
           "AND (:type IS NULL OR r.type = :type) " +
           "AND (:country IS NULL OR r.country = :country) " +
           "AND (:minPrice IS NULL OR r.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR r.price <= :maxPrice) " +
           "AND (:search IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "     OR LOWER(r.location) LIKE LOWER(CONCAT('%',:search,'%')))")
    List<Retreat> searchRetreats(
            @Param("type") String type,
            @Param("country") String country,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("search") String search
    );
}