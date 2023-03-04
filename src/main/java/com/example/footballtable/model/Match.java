package com.example.footballtable.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "matches",
        uniqueConstraints = @UniqueConstraint(columnNames={"home_team_id", "away_team_id"}))
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "home_team_id")
    private Team homeTeam;
    @ManyToOne
    @JoinColumn(name = "away_team_id")
    private Team awayTeam;
    private int home_team_goals;
    private int away_team_goals;

}
