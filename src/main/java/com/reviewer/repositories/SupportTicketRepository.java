package com.reviewer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reviewer.dao.SupportTicket;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

	public SupportTicket findByTicketId(Long ticket_id);

	public List<SupportTicket> findByUserId(Long user_id);
	
	public List<SupportTicket> findByAssignedRep(Long rep_id);

}
