package com.Friends.Bet.matches.dao;

import com.Friends.Bet.bet.dao.Bet;
import com.Friends.Bet.results.MatchResult;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fixtureId")
    private int fixtureId;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "match_start_date")
    private LocalDateTime matchStartDate;

    @Column(name = "host_goals")
    private int hostGoals;

    @Column(name = "guest_goals")
    private int guestGoals;

    @Column(name = "result")
    private MatchResult result;

    @OneToMany(mappedBy = "matches")
    private List<Bet> bets;


    public Match() {
    }

    public Match(Long id, String hostName, String guestName, int fixtureId, LocalDateTime matchStartDate, MatchResult result, int hostGoals, int guestGoals) {
        this.id = id;
        this.hostName = hostName;
        this.guestName = guestName;
        this.fixtureId = fixtureId;
        this.matchStartDate = matchStartDate;
        this.hostGoals = hostGoals;
        this.guestGoals = guestGoals;
        this.result = result;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(int fixtureId) {
        this.fixtureId = fixtureId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String host_name) {
        this.hostName = host_name;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guest_name) {
        this.guestName = guest_name;
    }

    public LocalDateTime getMatchStartDate() {
        return matchStartDate;
    }

    public void setMatchStartDate(LocalDateTime match_start_date) {
        this.matchStartDate = match_start_date;
    }

    public int getHostGoals() {
        return hostGoals;
    }

    public void setHostGoals(int hostGoals) {
        this.hostGoals = hostGoals;
    }

    public int getGuestGoals() {
        return guestGoals;
    }

    public void setGuestGoals(int guestGoals) {
        this.guestGoals = guestGoals;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public Collection<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }
}
