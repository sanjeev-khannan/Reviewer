package com.reviewer.dao;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "representative")
public class Representative {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rep_id")
	private Long repId;
	
	@Column(name = "open_ticket_count", nullable = false)
	private int openTicketCount;
	
	@Column(name = "total_ticket_count", nullable = false)
	private int totalTicketCount;
	
	@Column(name = "availability_status", nullable = false)
	private String availabilityStatus;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	public Long getRep_id() {
		return repId;
	}

	public void setRep_id(Long rep_id) {
		this.repId = rep_id;
	}

	public int getOpen_ticket_count() {
		return openTicketCount;
	}

	public void setOpen_ticket_count(int open_ticket_count) {
		this.openTicketCount = open_ticket_count;
	}

	public int getTotal_ticket_count() {
		return totalTicketCount;
	}

	public void setTotal_ticket_count(int total_ticket_count) {
		this.totalTicketCount = total_ticket_count;
	}

	public String getAvailability_status() {
		return availabilityStatus;
	}

	public void setAvailability_status(String availability_status) {
		this.availabilityStatus = availability_status;
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
}
