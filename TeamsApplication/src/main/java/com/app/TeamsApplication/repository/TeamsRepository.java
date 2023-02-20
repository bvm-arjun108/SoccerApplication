package com.app.TeamsApplication.repository;

import com.app.TeamsApplication.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<Teams, Long> {
    boolean existsByTeamName(String teamName);
    Teams findByTeamId(Long teamId);
}
