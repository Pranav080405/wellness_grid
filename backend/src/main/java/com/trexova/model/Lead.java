package com.trexova.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private String phone;
    private Long retreatId;
    private String retreatTitle;
    private String preferredDate;
    private String guestCount;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String status;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = "NEW";
    }

    // ===== GETTERS =====
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Long getRetreatId() { return retreatId; }
    public String getRetreatTitle() { return retreatTitle; }
    public String getPreferredDate() { return preferredDate; }
    public String getGuestCount() { return guestCount; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRetreatId(Long retreatId) { this.retreatId = retreatId; }
    public void setRetreatTitle(String retreatTitle) { this.retreatTitle = retreatTitle; }
    public void setPreferredDate(String preferredDate) { this.preferredDate = preferredDate; }
    public void setGuestCount(String guestCount) { this.guestCount = guestCount; }
    public void setMessage(String message) { this.message = message; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}