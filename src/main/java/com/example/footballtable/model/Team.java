package com.example.footballtable.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false, unique = true)
    private String name;
    @Column(length = 20, nullable = false)
    private String country;
    private String city;
    private int place;
    private int played;
    private int won;
    private int draw;
    private int lost;
    private int scored;
    private int missed;
    private int points;

}
