package com.trexova.service;

import com.trexova.model.Lead;
import com.trexova.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    public Lead createLead(Lead lead) {
        return leadRepository.save(lead);
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Lead> getLeadsByStatus(String status) {
        return leadRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public Lead updateLeadStatus(Long id, String status) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lead not found: " + id));
        lead.setStatus(status);
        return leadRepository.save(lead);
    }

    public void deleteLead(Long id) {
        leadRepository.deleteById(id);
    }

    public long countNewLeads() {
        return leadRepository.countByStatus("NEW");
    }

    public long countTotalLeads() {
        return leadRepository.count();
    }
}