package com.Friends.Bet.bet.dao;

import com.Friends.Bet.matches.dao.Match;
import com.Friends.Bet.results.BetResult;
import com.Friends.Bet.user.dao.User;

import javax.persistence.*;

@Entity
@Table(name = "bet")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "typ")
    private boolean typ;

    @Column(name = "is_accepted")
    private boolean accepted;

    @Column(name = "bet_result")
    private BetResult betResult;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "opponent_id")
    private User opponent;

    @ManyToOne
    private Match matches;


    public Bet() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOpponent() {
        return opponent;
    }

    public void setOpponent(User opponent) {
        this.opponent = opponent;
    }

    public Bet(Long id, boolean typ, boolean accepted, Match matches, User opponent, User user) {
        this.id = id;
        this.typ = typ;
        this.accepted = accepted;
        this.user = user;
        this.opponent = opponent;
        this.matches = matches;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isTyp() {
        return typ;
    }

    public Match getMatches() {
        return matches;
    }

    public void setMatches(Match matches) {
        this.matches = matches;
    }

    public void setTyp(boolean typ) {
        this.typ = typ;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public BetResult getBetResult() {
        return betResult;
    }

    public void setBetResult(BetResult betResult) {
        this.betResult = betResult;
    }
}
