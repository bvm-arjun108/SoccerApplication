package com.app.TeamsApplication.service;

import com.app.TeamsApplication.errors.TeamsErrorResponse;
import com.app.TeamsApplication.external.services.RatingService;
import com.app.TeamsApplication.model.Rating;
import com.app.TeamsApplication.model.Teams;
import com.app.TeamsApplication.model.TeamsSuccessResponse;
import com.app.TeamsApplication.repository.TeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamsService {
    public static final Instant startInstant = Instant.now();
    public static final Clock clock = Clock.systemUTC();
    @Autowired
    private TeamsRepository teamsRepository;
    @Autowired
    private RatingService ratingService;
    @Transactional
    public ResponseEntity<Object> registerTeam(Teams teams, BindingResult bindingResult) {
        // Perform input validation
        List<String> inputErrors = validateInput(teams, bindingResult);
        if (!inputErrors.isEmpty()) {
            // Return a user error response if input is invalid
            return createTeamErrorResponse(inputErrors);
        }
        try {
            // Check if the user already exists
            if (getTeamAlreadyExists(teams.getTeamName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(createTeamErrorResponse(Collections.singletonList("Team name already exists.")));
            }
            // Save the user to the database
            teamsRepository.save(teams);
            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createTeamResponse(teams.getTeamName()));
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createTeamErrorResponse(Collections.singletonList("An error occurred while processing the request.")));
        }
    }
    public Teams getTeamById(Long teamId) {
        Optional<Teams> optionalTeams = Optional.ofNullable(teamsRepository.findByTeamId(teamId));
        Teams teams = optionalTeams.orElseThrow(() -> new RuntimeException("Team with id " + teamId + " not found"));
        List<Rating> ratings = ratingService.getRatingsByTeamId(teamId);
        teams.setRating(ratings);
        return teams;
    }
    public List<Teams> getAllTeams() {
        List<Teams> teams = teamsRepository.findAll();
        List<Rating> ratings = ratingService.getAllRatings();
        for (Teams team : teams) {
            List<Rating> teamRatings = new ArrayList<>();
            for (Rating rating : ratings) {
                if (team.getTeamId().equals(rating.getTeamId())) {
                    teamRatings.add(rating);
                }
            }
            team.setRating(teamRatings);
        }
        return teams;
    }

    private List<String> validateInput(Teams user, BindingResult bindingResult) {
        TeamsErrorResponse teamsErrorResponse = new TeamsErrorResponse();
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.toList());
            teamsErrorResponse.setErrorMessage(errorMessages);
            return errorMessages;
    }
    private ResponseEntity<Object> createTeamErrorResponse(List<String> errorMessages) {
        TeamsErrorResponse response = new TeamsErrorResponse(
                TeamsErrorResponse.calculateExecutionTimeInMillis(startInstant, clock),
                Instant.now(clock).toString(),
                "An error occurred while processing the request.",
                errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    private ResponseEntity<Object> createTeamResponse(String username) {
        TeamsSuccessResponse response = new TeamsSuccessResponse(
                TeamsSuccessResponse.calculateExecutionTimeInMillis(startInstant, clock),
                Instant.now(clock).toString(),
                username + " registered successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    public boolean getTeamAlreadyExists(String teamName) {
        return teamsRepository.existsByTeamName(teamName);
    }

}
