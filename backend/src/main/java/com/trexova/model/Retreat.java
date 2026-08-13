package com.trexova.model;

import jakarta.persistence.*;

@Entity
@Table(name = "retreats")
public class Retreat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    private String country;
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;
    private String duration;
    private String imageUrl;
    private Double rating;
    private Integer reviewCount;
    private Boolean featured;
    private Boolean active;

    @Column(columnDefinition = "TEXT")
    private String amenities;

    @Column(columnDefinition = "TEXT")
    private String availableDates;

    // ===== GETTERS =====
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public String getCountry() { return country; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public String getDuration() { return duration; }
    public String getImageUrl() { return imageUrl; }
    public Double getRating() { return rating; }
    public Integer getReviewCount() { return reviewCount; }
    public Boolean getFeatured() { return featured; }
    public Boolean getActive() { return active; }
    public String getAmenities() { return amenities; }
    public String getAvailableDates() { return availableDates; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setCountry(String country) { this.country = country; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(Double price) { this.price = price; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
    public void setFeatured(Boolean featured) { this.featured = featured; }
    public void setActive(Boolean active) { this.active = active; }
    public void setAmenities(String amenities) { this.amenities = amenities; }
    public void setAvailableDates(String availableDates) { this.availableDates = availableDates; }
}