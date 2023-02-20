package com.example.RatingService.model;


import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ratings") // Similar to Entity. As entity is a collection in mongoDB
public class Rating {
    @Id
    private String ratingId;
    private Long userId;
    private Long teamId;
    private String leagueId;
    private int rating;
    private String comment;
}
