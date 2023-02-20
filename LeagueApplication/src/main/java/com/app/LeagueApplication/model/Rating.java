package com.app.LeagueApplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private String ratingId;
    private Long userId;
    private Long teamId;
    private String leagueId;
    private int rating;
    private String comment;
}
