package com.app.TeamsApplication.controller;

import com.app.TeamsApplication.model.Teams;
import com.app.TeamsApplication.service.TeamsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamsController {
    @Autowired
    private TeamsService teamsService;
    @PostMapping("/register")
    public ResponseEntity<Object> registerTeam(@Valid @RequestBody Teams teams, BindingResult bindingResult) {
        return teamsService.registerTeam(teams, bindingResult);
    }
    @GetMapping("/{teamId}")
    public Teams getTeamById(@PathVariable("teamId") Long teamId) {
        return teamsService.getTeamById(teamId);
    }
    @GetMapping
    public List<Teams> getAllTeams() {
        return teamsService.getAllTeams();
    }
}
