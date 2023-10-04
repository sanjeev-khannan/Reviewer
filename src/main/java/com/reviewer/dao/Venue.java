package com.reviewer.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "venue")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Integer venueId;

    @Column(name = "venue_type")
    private String venueType;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "venue_email")
    private String venueEmail;

    @Column(name = "venue_password")
    private String venuePassword;

    @Column(name = "venue_address1")
    private String venueAddress1;

    @Column(name = "venue_address2")
    private String venueAddress2;

    @Column(name = "city")
    private String city;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "mobilenumber")
    private String mobileNumber;

    @Column(name = "venue_description")
    private String venueDescription;

    @Column(name = "timing")
    private String timing;

    @Column(name = "website")
    private String website;

    @Column(name = "rating")
    private float rating;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "min_price")
    private int minPrice;

    @Column(name = "max_price")
    private int maxPrice;

	@Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    public Integer getVenueId() {
		return venueId;
	}

	public void setVenueId(Integer venueId) {
		this.venueId = venueId;
	}

	public String getVenueType() {
		return venueType;
	}

	public void setVenueType(String venueType) {
		this.venueType = venueType;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getVenueEmail() {
		return venueEmail;
	}

	public void setVenueEmail(String venueEmail) {
		this.venueEmail = venueEmail;
	}

	public String getVenuePassword() {
		return venuePassword;
	}

	public void setVenuePassword(String venuePassword) {
		this.venuePassword = venuePassword;
	}

	public String getVenueAddress1() {
		return venueAddress1;
	}

	public void setVenueAddress1(String venueAddress1) {
		this.venueAddress1 = venueAddress1;
	}

	public String getVenueAddress2() {
		return venueAddress2;
	}

	public void setVenueAddress2(String venueAddress2) {
		this.venueAddress2 = venueAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getVenueDescription() {
		return venueDescription;
	}

	public void setVenueDescription(String venueDescription) {
		this.venueDescription = venueDescription;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

    public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = this.createdAt;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
