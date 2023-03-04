package com.example.footballtable.repositories;

import com.example.footballtable.model.Match;
import com.example.footballtable.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MatchRepository extends JpaRepository<Match, Long> {

}
