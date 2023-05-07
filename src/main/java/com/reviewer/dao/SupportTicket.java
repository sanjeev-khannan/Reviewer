package com.reviewer.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customersupport")
public class SupportTicket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private Long ticketId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "subject", nullable = false)
	private String subject;

	@Column(name = "assigned_rep", nullable = false)
	private Long assignedRep;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "comments", nullable = false)
	private String comments;

	@Column(name = "message", nullable = false)
	private String message;

	public Long getTicket_id() {
		return ticketId;
	}

	public void setTicket_id(Long ticket_id) {
		this.ticketId = ticket_id;
	}

	public Long getUser_id() {
		return userId;
	}

	public void setUser_id(Long user_id) {
		this.userId = user_id;
	}

	public Long getAssigned_rep() {
		return assignedRep;
	}

	public void setAssigned_rep(Long assigned_rep) {
		this.assignedRep = assigned_rep;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}