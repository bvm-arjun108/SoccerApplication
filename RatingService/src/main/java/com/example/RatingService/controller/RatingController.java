package com.example.RatingService.controller;

import com.example.RatingService.model.Rating;
import com.example.RatingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {
    @Autowired
    private  RatingService ratingService;
    @PostMapping("/create")
    public ResponseEntity<Rating> create(@RequestBody Rating rating) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ratingService.createRating(rating));
    }
    @GetMapping("/all")
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getRatings());
    }
    @GetMapping("user/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(ratingService.getRatingsByUserId(userId));
    }
    @GetMapping("leagues/{leagueId}")
    public ResponseEntity<List<Rating>> getRatingsByLeagueId(@PathVariable String leagueId){
        return ResponseEntity.ok(ratingService.getRatingsByLeagueId(leagueId));
    }
    @GetMapping("team/{teamId}")
    public ResponseEntity<List<Rating>> getRatingsByTeamId(@PathVariable Long teamId){
        return ResponseEntity.ok(ratingService.getRatingsByTeamId(teamId));
    }
}
