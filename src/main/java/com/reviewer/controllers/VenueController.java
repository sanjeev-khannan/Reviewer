package com.reviewer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reviewer.dao.Venue;
import com.reviewer.pojos.SearchQuery;
import com.reviewer.services.VenueService;

@RestController
public class VenueController {

  @Autowired
  public VenueService venueService;

  @RequestMapping(value = "/venue", method = RequestMethod.POST)
  public ResponseEntity<List<Venue>> searchVenue(@RequestBody SearchQuery searchQuery) throws Exception {
    return ResponseEntity.ok(venueService.findVenueWithCriteria(searchQuery));
  }
}
