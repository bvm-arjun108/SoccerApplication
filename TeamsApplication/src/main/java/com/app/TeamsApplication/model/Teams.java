package com.app.TeamsApplication.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    @NotNull
    private String teamName;
    @NotNull
    private String ownerName;
    @NotNull
    private String managerName;
    @NotNull
    private String leagueName;
    @Transient
    private List<Rating> rating;
}
