package com.Friends.Bet.bet.dao;

import com.Friends.Bet.matches.dao.Match;
import com.Friends.Bet.user.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findBetsByOpponentAndAcceptedFalse(User opponent);

    @Query("SELECT b FROM Bet b WHERE (b.accepted = true) AND (b.user = ?1 OR b.opponent = ?2)")
    Page<Bet> findBetsByUserOrOpponent(User user, User opponent, Pageable pageable);

    List<Bet> findAllByBetResultIsNull();

    @Query("SELECT b FROM Bet b WHERE (b.matches = ?1 AND (b.user = ?2 OR b.opponent = ?2))")
    List<Bet> findAllBets(Match match, User user);
}
