package com.example.footballtable.controller;

import com.example.footballtable.model.Team;
import com.example.footballtable.repositories.MatchRepository;
import com.example.footballtable.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;
//    @Autowired
//    private LeagueRepo leagueRepo;

    @GetMapping("/createTeam")
    public String showNewTeamForm(Model model){
//        List<League> leagueList = leagueRepo.findAll();
        model.addAttribute("team", new Team());
        return "team_form";
    }

    @GetMapping("/teams")
    public String listTeams(Model model){
        List<Team> teamList = teamRepository.findAll();
        model.addAttribute("teamList", teamList);
        return "teams";
    }

    @PostMapping("/team/save")
    public String saveTeam(Team team){

        teamRepository.save(team);
        return "redirect:/teams";
    }

    @GetMapping("/team/edit/{id}")
    public String showEditTeamForm(@PathVariable("id") Long id, Model model){
        Team team = teamRepository.findById(id).get();
        model.addAttribute("team", team);
        return "team_form";
    }

    @GetMapping("/team/delete/{id}")
    public String deleteTeam(@PathVariable("id") Long id, Model model){
        teamRepository.deleteById(id);
        return "redirect:/teams";
    }

    @GetMapping("/table")
    public String showTable(Model model){
        List<Team> teamList = teamRepository.findAll();

        Collections.sort(teamList, Comparator.comparingInt(Team::getPoints));
        List<Team> sorted = teamList.stream().sorted(Comparator.comparingInt(Team::getPoints).reversed())
                .collect(Collectors.toList());

        int place = 1;
        for(Team team : sorted){
            team.setPlace(place);
            place++;
        }

        Collections.sort(teamList, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {

                return o2.getPoints() - o1.getPoints();
            }
        });

        model.addAttribute("teamList", teamList);
        return "league";
    }
}
