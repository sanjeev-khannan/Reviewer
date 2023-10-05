package com.reviewer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.dao.Venue;
import com.reviewer.services.VenueService;

@RestController
public class VenueController {
 
    @Autowired
    public VenueService venueService;

	@RequestMapping(value = "/venue", method = RequestMethod.GET)
    public ResponseEntity<List<Venue>> searchVenue(@RequestParam String search_phrase){
        // venueService.getAllVenues();
		return ResponseEntity.ok(venueService.findVenueWithCriteria('%'+search_phrase.toLowerCase()+'%'));
    }
}
