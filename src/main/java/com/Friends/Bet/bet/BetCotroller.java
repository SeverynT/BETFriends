package com.Friends.Bet.bet;

import com.Friends.Bet.bet.dao.Bet;
import com.Friends.Bet.bet.dao.BetRepository;
import com.Friends.Bet.matches.dao.Match;
import com.Friends.Bet.user.dao.User;
import com.Friends.Bet.user.dao.UserRepository;
import com.Friends.Bet.matches.IMatchService;
import com.Friends.Bet.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BetCotroller {

    @Autowired
    private IBetService iBetService;

    @Autowired
    private IMatchService iMatchService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private BetRepository betRepository;


    @PostMapping("/saveBet")
    public String saveBet(@ModelAttribute("bet") Bet bet) {

        iBetService.saveBet(bet);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Match match = iMatchService.getMatchById(id);

        User currentUser = iUserService.getUser();

        List<Bet> bets = betRepository.findAllBets(match, currentUser);

        List<User> users = iBetService.findOpponents(bets);

        model.addAttribute("users", users);

        Bet bet = new Bet();
        bet.setMatches(match);
        bet.setUser(currentUser);
        model.addAttribute("bet", bet);

        return "new_bet";
    }

    @GetMapping("/friendChallenge")
    public String viewFriendlyChallenge(Model model) {

        List<Bet> bets = betRepository.findBetsByOpponentAndAcceptedFalse(iUserService.getUser());

        model.addAttribute("friendBet", bets);

        return "friend_challenge";
    }

    @GetMapping("/myBets")
    public String viewMyBets(Model model) {

        iBetService.fillBetResultInDB();

        return findPaginated(1, "matches.matchStartDate", "desc", model);
    }

    @GetMapping("/pages/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;

        Page<Bet> page = iBetService.findPaginatedBets(pageNo, pageSize, sortField, sortDir);
        List<Bet> bets = page.getContent();
        User user = iUserService.getUser();


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("bets", bets);
        model.addAttribute("currentUser", user);

        return "my_bets";


    }

    @GetMapping("/deleteBet/{id}")
    public String rejectBet(@PathVariable(value = "id") long id) {

        this.iBetService.deleteBetById(id);

        return "redirect:/friendChallenge";
    }

    @GetMapping("acceptBet/{id}")
    public String acceptBet(@PathVariable(value = "id") long id) {

        Bet bet = iBetService.getBetById(id);
        bet.setAccepted(true);
        betRepository.save(bet);

        return "redirect:/friendChallenge";
    }
}
