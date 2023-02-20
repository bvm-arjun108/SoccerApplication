package com.app.LeagueApplication.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.app.LeagueApplication.model.League;
import com.app.LeagueApplication.service.LeagueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/league")
public class LeagueController {
    @Autowired
    private LeagueService leagueService;
    private Logger logger = LoggerFactory.getLogger(LeagueController.class);
    @PostMapping("/create")
    public ResponseEntity<League> createLeague(@RequestBody League league) {
        League createdLeague = leagueService.createLeague(league);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLeague);
    }
    @GetMapping("/{leagueId}")
    public ResponseEntity<League> getLeague (@PathVariable String leagueId) {
        return ResponseEntity.ok(leagueService.getLeague(leagueId));
    }
    @GetMapping
    public ResponseEntity<List<League>> getAllLeagues() {
        return ResponseEntity.ok(leagueService.getAllLeagues());
    }
}
