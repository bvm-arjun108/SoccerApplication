package com.app.LeagueApplication.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.app.LeagueApplication.exceptions.ResourceNotFoundException;
import com.app.LeagueApplication.model.League;
import com.app.LeagueApplication.model.Rating;
import com.app.LeagueApplication.repository.LeaguesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LeagueService {
    @Autowired
    private LeaguesRepository leaguesRepository;
    @Autowired
    private AmazonDynamoDB amazonDynamoDB;
    @Autowired
    private RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(LeagueService.class);
    public League createLeague(League league) {
        return leaguesRepository.save(league);
    }
    public League getLeague(String id) {
        League league = leaguesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("League not found with id: " + id));
        // Fetch rating of the above league from Rating Service
        // http://localhost:9094/api/v1/rating/leagues/b09f3265-2f69-4c41-9a63-32a10e83c78e
        ArrayList<Rating> ratingsOfLeague = restTemplate.getForObject("http://RATINGAPPLICATION/api/v1/rating/leagues/" + league.getId(), ArrayList.class);
        logger.info("{} ", ratingsOfLeague);
        league.setRatings(ratingsOfLeague);
        return league;
    }

    public List<League> getAllLeagues() {
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<League> leagues = mapper.scan(League.class, scanExpression);

        Map<String, League> leagueMap = new HashMap<>();
        for (League league : leagues) {
            league.setRatings(new ArrayList<>());
            leagueMap.put(league.getId(), league);
        }

        Rating[] ratings = restTemplate.getForObject("http://RATINGAPPLICATION/api/v1/rating/all", Rating[].class);
        for (Rating rating : ratings) {
            League league = leagueMap.get(rating.getLeagueId());
            if (league != null) {
                league.getRatings().add(rating);
            }
        }
        return leagues;
    }
}
