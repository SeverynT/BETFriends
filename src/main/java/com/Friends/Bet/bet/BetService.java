package com.Friends.Bet.bet;

import com.Friends.Bet.bet.dao.Bet;
import com.Friends.Bet.bet.dao.BetRepository;
import com.Friends.Bet.matches.dao.Match;
import com.Friends.Bet.results.BetResult;
import com.Friends.Bet.user.dao.User;
import com.Friends.Bet.points.IPointService;
import com.Friends.Bet.user.IUserService;
import com.Friends.Bet.user.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BetService implements IBetService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IPointService iPointService;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public Optional<Bet> findById(Long id) {
        return betRepository.findById(id);
    }

    public Iterable<Bet> findAll() {
        return betRepository.findAll();
    }


    @Override
    public void saveBet(Bet bet) {
        this.betRepository.save(bet);
    }

    @Override
    public void deleteBetById(long id) {
        this.betRepository.deleteById(id);
    }

    @Override
    public Bet getBetById(long id) {
        Optional<Bet> optional = betRepository.findById(id);
        Bet bet = null;
        if (optional.isPresent()) {
            bet = optional.get();
        } else {
            throw new RuntimeException("Bet doesn't exists for id:" + id);
        }
        return bet;
    }

    @Override
    public Page<Bet> findPaginatedBets(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        User user = iUserService.getUser();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.betRepository.findBetsByUserOrOpponent(user, user, pageable);
    }

    @Override
    public List<User> findOpponents(List<Bet> bets) {

        Set<Long> opponents = new HashSet<>();
        List<User> users;

        if (!bets.isEmpty()) {
            for (Bet bet : bets) {
                opponents.add(bet.getUser().getId());
                opponents.add(bet.getOpponent().getId());
            }

            users = userRepository.findAllOpponent(opponents);

        } else {
            users = userRepository.findAll();
        }

        return users;
    }

    @Override
    public void fillBetResultInDB() {

        List<Bet> bets = betRepository.findAllByBetResultIsNull();

        for (Bet bet : bets) {
            bet.setBetResult(settlementOfTheBet(bet));
            betRepository.save(bet);
            iPointService.pointSettlementOfTheBet(bet);
        }

    }

    private BetResult settlementOfTheBet(Bet bet) {
        Match match = bet.getMatches();
        return BetResult.fromBet(match.getResult(), bet.isTyp());
    }

}





