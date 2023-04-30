package com.reviewer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.reviewer.dao.SupportTicket;
import com.reviewer.dao.User;
import com.reviewer.pojos.CreateTicketForm;
import com.reviewer.repositories.SupportTicketRepository;

@Service
public class SupportTicketServiceImpl {

	@Autowired
	private SupportTicketRepository supportTicketRepository;

	public SupportTicket loadTicketByTicketId(Long ticket_id) {

		SupportTicket supportTicket = this.supportTicketRepository.findByTicketId(ticket_id);
		return supportTicket;
	}

	public List<SupportTicket> loadTicketsByUserId(Long user_id) {

		List<SupportTicket> supportTickets = this.supportTicketRepository.findByUserId(user_id);
		return supportTickets;
	}

	public boolean createTicket(CreateTicketForm ticket) {
		try {

			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SupportTicket supportTicket = new SupportTicket();
			supportTicket.setUser_id(user.getUserId());
			supportTicket.setSubject(ticket.getSubject());
			supportTicket.setMessage(ticket.getMessage());
			supportTicket.setComments("Ticket is on process, will be resolved soon");
			supportTicket.setStatus("OPEN");
			supportTicket.setAssigned_rep(1002l); // Have to be changed automatically

			supportTicketRepository.save(supportTicket);
			System.out.println("SupportTicketService: Create Ticket - success");
			return true;
		} catch (Exception e) {
			System.out.println("SupportTicketService: Exception while Creating Ticket");
			throw (e);
		}
	}

	public boolean validatePartialTicket(CreateTicketForm ticket){
		if(ticket.getSubject()==null || ticket.getMessage()==null || 
		ticket.getMessage().equals("") || ticket.getSubject().equals("")){
			return false;
		}
		return true;
	}
}
