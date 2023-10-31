package com.reviewer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.dao.Venue;
import com.reviewer.pojos.SearchQuery;
import com.reviewer.services.VenueService;

@RestController
public class VenueController {

  @Autowired
  public VenueService venueService;

  @RequestMapping(value = "/venue/getfromquery", method = RequestMethod.POST)
  public ResponseEntity<List<Venue>> searchVenueFromQuery(@RequestBody SearchQuery searchQuery) throws Exception {
    return ResponseEntity.ok(venueService.findVenueWithCriteria(searchQuery));
  }

  @RequestMapping(value = "/venue/getfromid", method = RequestMethod.GET)
  public ResponseEntity<Venue> searchVenueById(@RequestParam Long venue_id) throws Exception {
    return ResponseEntity.ok(venueService.findByVenueId(venue_id));
  }
}
