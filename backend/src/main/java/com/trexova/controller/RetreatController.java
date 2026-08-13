package com.trexova.controller;

import com.trexova.model.Retreat;
import com.trexova.service.RetreatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/retreats")
public class RetreatController {

    @Autowired
    private RetreatService retreatService;

    // GET /api/retreats
    @GetMapping
    public ResponseEntity<List<Retreat>> getAllRetreats() {
        return ResponseEntity.ok(retreatService.getAllRetreats());
    }

    // GET /api/retreats/search?type=yoga&country=India&minPrice=500&maxPrice=3000&search=bali
    @GetMapping("/search")
    public ResponseEntity<List<Retreat>> searchRetreats(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(retreatService.searchRetreats(type, country, minPrice, maxPrice, search));
    }

    // GET /api/retreats/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Retreat> getRetreatById(@PathVariable Long id) {
        return retreatService.getRetreatById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/retreats
    @PostMapping
    public ResponseEntity<Retreat> createRetreat(@RequestBody Retreat retreat) {
        return ResponseEntity.status(HttpStatus.CREATED).body(retreatService.createRetreat(retreat));
    }

    // PUT /api/retreats/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Retreat> updateRetreat(@PathVariable Long id, @RequestBody Retreat retreat) {
        try {
            return ResponseEntity.ok(retreatService.updateRetreat(id, retreat));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/retreats/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRetreat(@PathVariable Long id) {
        try {
            retreatService.deleteRetreat(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/retreats/admin/all  (includes inactive)
    @GetMapping("/admin/all")
    public ResponseEntity<List<Retreat>> getAllAdmin() {
        return ResponseEntity.ok(retreatService.getAllRetreatsAdmin());
    }
}