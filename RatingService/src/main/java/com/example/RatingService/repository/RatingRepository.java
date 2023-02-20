package com.example.RatingService.repository;

import com.example.RatingService.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    // Custom finder methods
    List<Rating> findByUserId(Long userId);
    List<Rating> findByLeagueId(String leagueId);
    List<Rating> findByTeamId(Long teamId);
}
