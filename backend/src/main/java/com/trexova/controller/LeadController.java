package com.trexova.controller;

import com.trexova.model.Lead;
import com.trexova.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    @Autowired
    private LeadService leadService;

    // POST /api/leads  ← called when user submits Enquire Now form
    @PostMapping
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadService.createLead(lead));
    }

    // GET /api/leads  or  GET /api/leads?status=NEW
    @GetMapping
    public ResponseEntity<List<Lead>> getAllLeads(@RequestParam(required = false) String status) {
        List<Lead> leads = (status != null)
                ? leadService.getLeadsByStatus(status)
                : leadService.getAllLeads();
        return ResponseEntity.ok(leads);
    }

    // PATCH /api/leads/{id}/status   body: { "status": "REPLIED" }
    @PatchMapping("/{id}/status")
    public ResponseEntity<Lead> updateStatus(@PathVariable Long id,
                                              @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(leadService.updateLeadStatus(id, body.get("status")));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/leads/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        leadService.deleteLead(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/leads/stats
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalLeads", leadService.countTotalLeads());
        stats.put("newLeads", leadService.countNewLeads());
        return ResponseEntity.ok(stats);
    }
}