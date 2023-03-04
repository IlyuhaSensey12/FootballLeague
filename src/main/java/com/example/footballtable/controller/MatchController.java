package com.example.footballtable.controller;

import com.example.footballtable.model.Match;
import com.example.footballtable.model.Team;
import com.example.footballtable.repositories.MatchRepository;
import com.example.footballtable.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MatchController {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;

    @GetMapping("/createMatch")
    public String showNewMatchForm(Model model){
        List<Team> teamList= teamRepository.findAll();
        model.addAttribute("match", new Match());
        model.addAttribute("teamList", teamList);
        return "match_form";
    }

    @PostMapping("/match/save")
    public String saveMatch(Match match, RedirectAttributes ra, Model model){
        if(match.getAwayTeam().getName() != match.getHomeTeam().getName()) {
            int home = match.getHome_team_goals();
            int away = match.getAway_team_goals();

            match.getAwayTeam().setScored(match.getAwayTeam().getScored() + away);
            match.getHomeTeam().setScored(match.getHomeTeam().getScored() + home);
            match.getAwayTeam().setMissed(match.getHomeTeam().getScored());
            match.getHomeTeam().setMissed(match.getAwayTeam().getScored());


            if (home > away) {
                match.getHomeTeam().setPlayed(match.getHomeTeam().getPlayed() + 1);
                match.getHomeTeam().setWon(match.getHomeTeam().getWon() + 1);
                match.getHomeTeam().setPoints(match.getHomeTeam().getPoints() + 3);

                match.getAwayTeam().setPlayed(match.getAwayTeam().getPlayed() + 1);
                match.getAwayTeam().setLost(match.getAwayTeam().getLost() + 1);

            } else if (home > away) {
                match.getAwayTeam().setPlayed(match.getAwayTeam().getPlayed() + 1);
                match.getAwayTeam().setWon(match.getAwayTeam().getWon() + 1);
                match.getAwayTeam().setPoints(match.getAwayTeam().getPoints() + 3);

                match.getHomeTeam().setPlayed(match.getHomeTeam().getPlayed() + 1);
                match.getHomeTeam().setLost(match.getHomeTeam().getLost() + 1);

            } else if (home == away) {
                match.getHomeTeam().setPlayed(match.getHomeTeam().getPlayed() + 1);
                match.getAwayTeam().setPlayed(match.getAwayTeam().getPlayed() + 1);
                match.getHomeTeam().setDraw(match.getHomeTeam().getDraw() + 1);
                match.getAwayTeam().setDraw(match.getAwayTeam().getDraw() + 1);
                match.getHomeTeam().setPoints(match.getAwayTeam().getPoints() + 1);
                match.getAwayTeam().setPoints(match.getAwayTeam().getPoints() + 1);
            }
            ra.addFlashAttribute("message","The match has been saved");
            matchRepository.save(match);
        }
        else if(match.getAwayTeam().getName() == match.getHomeTeam().getName()){
            ra.addFlashAttribute("message2", "Teams can not be same");
        }
        return "redirect:/matches";
    }

    @GetMapping("/matches")
    public String listMatch(Model model){
        List<Match> matchList = matchRepository.findAll();
        model.addAttribute("matchList", matchList);
        return "matches";
    }

    @GetMapping("/match/edit/{id}")
    public String showEditMatchForm(@PathVariable("id") Long id, Model model){
        Match match = matchRepository.findById(id).get();
        model.addAttribute("match",match);

        List<Team> teamList= teamRepository.findAll();
        model.addAttribute("teamList", teamList);
        return "match_form";
    }

    @GetMapping("/match/delete/{id}")
    public String deleteMatch(@PathVariable("id") Long id, Model model){
        matchRepository.deleteById(id);
        return "redirect:/matches";
    }

}
