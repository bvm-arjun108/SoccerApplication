package com.example.RatingService.service;

import com.example.RatingService.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    Rating createRating(Rating rating);
    List<Rating> getRatings();
    List<Rating> getRatingsByUserId(Long userId);
    List<Rating> getRatingsByLeagueId(String leagueId);
    List<Rating> getRatingsByTeamId(Long teamId);
}
