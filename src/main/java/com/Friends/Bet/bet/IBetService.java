package com.Friends.Bet.bet;

import com.Friends.Bet.bet.dao.Bet;
import com.Friends.Bet.user.dao.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBetService {
    void saveBet(Bet bet);

    void deleteBetById(long id);

    Bet getBetById(long id);

    Page<Bet> findPaginatedBets(int pageNo, int pageSize, String sortField, String sortDirection);

    void fillBetResultInDB();

    List<User> findOpponents(List<Bet> bets);
}
