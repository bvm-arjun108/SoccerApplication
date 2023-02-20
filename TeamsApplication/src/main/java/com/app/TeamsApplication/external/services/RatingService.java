package com.app.TeamsApplication.external.services;

import com.app.TeamsApplication.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATINGAPPLICATION")
public interface RatingService {
    @GetMapping("/api/v1/rating/team/{teamId}")
    List<Rating> getRatingsByTeamId(@PathVariable Long teamId);

    @GetMapping("/api/v1/rating/all")
    List<Rating> getAllRatings();

}
