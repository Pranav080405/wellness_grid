package com.trexova.service;

import com.trexova.model.Retreat;
import com.trexova.repository.RetreatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RetreatService {

    @Autowired
    private RetreatRepository retreatRepository;

    public List<Retreat> getAllRetreats() {
        return retreatRepository.findByActiveTrue();
    }

    public Optional<Retreat> getRetreatById(Long id) {
        return retreatRepository.findById(id);
    }

    public List<Retreat> searchRetreats(String type, String country,
                                         Double minPrice, Double maxPrice, String search) {
        return retreatRepository.searchRetreats(type, country, minPrice, maxPrice, search);
    }

    public Retreat createRetreat(Retreat retreat) {
        retreat.setActive(true);
        if (retreat.getFeatured() == null) retreat.setFeatured(false);
        return retreatRepository.save(retreat);
    }

    public Retreat updateRetreat(Long id, Retreat updated) {
        Retreat existing = retreatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retreat not found: " + id));
        existing.setTitle(updated.getTitle());
        existing.setLocation(updated.getLocation());
        existing.setCountry(updated.getCountry());
        existing.setType(updated.getType());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setDuration(updated.getDuration());
        existing.setImageUrl(updated.getImageUrl());
        existing.setAmenities(updated.getAmenities());
        existing.setAvailableDates(updated.getAvailableDates());
        existing.setFeatured(updated.getFeatured());
        return retreatRepository.save(existing);
    }

    public void deleteRetreat(Long id) {
        Retreat retreat = retreatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Retreat not found: " + id));
        retreat.setActive(false);  // soft delete
        retreatRepository.save(retreat);
    }

    public List<Retreat> getAllRetreatsAdmin() {
        return retreatRepository.findAll();
    }

    public long getTotalCount() {
        return retreatRepository.findByActiveTrue().size();
    }
}