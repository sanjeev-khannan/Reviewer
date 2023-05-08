package com.reviewer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.dao.SupportTicket;
import com.reviewer.dao.User;
import com.reviewer.pojos.CreateTicketForm;
import com.reviewer.pojos.ReviewerResponse;
import com.reviewer.services.SupportTicketServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/support")
public class CustomerSupportController {

	@Autowired
	private SupportTicketServiceImpl supportTicketService;
    
    @RequestMapping(path = "/getticketbyid", method = RequestMethod.POST)
	public ResponseEntity<?> getTicket(@RequestParam Long ticket_id) {

		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			SupportTicket supportTicket = supportTicketService.loadTicketByTicketId(ticket_id);
			if (supportTicket != null && supportTicket.getUser_id().equals(user.getUserId())) {
				return ResponseEntity.ok(supportTicket);
			} 
			else if(supportTicket == null){
				supportTicket = new SupportTicket();
				supportTicket.setStatus("INVALID_TICKET_ID");
				return ResponseEntity.badRequest().body(supportTicket);
			}
			else {
				return ResponseEntity.badRequest().body(new ReviewerResponse(HttpServletResponse.SC_UNAUTHORIZED, 
				"UNAUTHORIZED_TICKET_ACCESS"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			SupportTicket supportTicket = new SupportTicket();
			supportTicket.setStatus("Unexpected Error occured while getting Ticket!");
			return ResponseEntity.internalServerError().body(supportTicket);
		}

	}

    @RequestMapping(path = "/getmytickets", method = RequestMethod.POST)
	public ResponseEntity<?> getTickets() {
        System.out.println("Entered getmytickets");
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<SupportTicket> supportTickets = supportTicketService.loadTicketsByUserId(user.getUserId());
			if (supportTickets != null && user!=null) {
				return ResponseEntity.ok(supportTickets);
			} 
			else if(supportTickets == null){
				return ResponseEntity.badRequest().body(new ReviewerResponse(HttpServletResponse.SC_NO_CONTENT, 
                "NO_TICKETS_FOUND"));
			}
			else {
				return ResponseEntity.badRequest().body(new ReviewerResponse(HttpServletResponse.SC_UNAUTHORIZED, 
				"UNAUTHORIZED_ACCESS"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new ReviewerResponse(HttpServletResponse.SC_CONFLICT, 
            "Unexpected Error occured while getting Ticket!"));
		}
	}

	@RequestMapping(path = "/createticket", method = RequestMethod.POST)
	public ResponseEntity<?> createticket(@RequestBody CreateTicketForm ticket_partial) {
        System.out.println("Entered Create Ticket");
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (user!=null) {
				if(supportTicketService.validatePartialTicket(ticket_partial)){
					supportTicketService.createTicket(ticket_partial);
					return ResponseEntity.ok(new ReviewerResponse(HttpServletResponse.SC_OK, "TICKET_CREATED"));
				}
				return ResponseEntity.badRequest().body(new ReviewerResponse(HttpServletResponse.SC_BAD_REQUEST, "INVALID_TICKET"));
			}
			else {
				return ResponseEntity.badRequest().body(new ReviewerResponse(HttpServletResponse.SC_UNAUTHORIZED, 
				"UNAUTHORIZED_ACCESS"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new ReviewerResponse(HttpServletResponse.SC_CONFLICT, 
            "Unexpected Error occured while getting Ticket!"));
		}
	}
}
