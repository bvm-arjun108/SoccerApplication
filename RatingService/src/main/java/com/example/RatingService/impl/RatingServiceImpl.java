package com.example.RatingService.impl;

import com.example.RatingService.model.Rating;
import com.example.RatingService.repository.RatingRepository;
import com.example.RatingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }
    @Override
    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }
    @Override
    public List<Rating> getRatingsByUserId(Long userId) {
        return ratingRepository.findByUserId(userId);
    }
    @Override
    public List<Rating> getRatingsByLeagueId(String leagueId) {
        return ratingRepository.findByLeagueId(leagueId);
    }

    @Override
    public List<Rating> getRatingsByTeamId(Long teamId) {
        return ratingRepository.findByTeamId(teamId);
    }

}
