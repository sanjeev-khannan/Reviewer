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
	private Long rep_id;
	
	@Column(name = "open_ticket_count", nullable = false)
	private int open_ticket_count;
	
	@Column(name = "total_ticket_count", nullable = false)
	private int total_ticket_count;
	
	@Column(name = "availability_status", nullable = false)
	private String availability_status;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	public Long getRep_id() {
		return rep_id;
	}

	public void setRep_id(Long rep_id) {
		this.rep_id = rep_id;
	}

	public int getOpen_ticket_count() {
		return open_ticket_count;
	}

	public void setOpen_ticket_count(int open_ticket_count) {
		this.open_ticket_count = open_ticket_count;
	}

	public int getTotal_ticket_count() {
		return total_ticket_count;
	}

	public void setTotal_ticket_count(int total_ticket_count) {
		this.total_ticket_count = total_ticket_count;
	}

	public String getAvailability_status() {
		return availability_status;
	}

	public void setAvailability_status(String availability_status) {
		this.availability_status = availability_status;
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
