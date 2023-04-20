package com.reviewer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewer.dao.SupportTicket;
import com.reviewer.repositories.SupportTicketRepository;

@Service
public class SupportTicketServiceImpl {

	@Autowired
	private SupportTicketRepository supportTicketRepository;

	public SupportTicket loadTicketByTicketId(Long ticket_id){

		SupportTicket supportTicket = this.supportTicketRepository.findByTicketId(ticket_id);
		return supportTicket;
	}
}
