package com.Friends.Bet.matches.dao;

import com.Friends.Bet.matches.dao.Match;
import com.Friends.Bet.results.MatchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findAllByResultAndMatchStartDateAfter(MatchResult matchResult, LocalDateTime localDateTime, Pageable pageable);

}
