package com.Friends.Bet.matches;

import com.Friends.Bet.matches.dao.Match;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MatchController {

    @Autowired
    private IMatchService iMatchService;

    @GetMapping("/")
    public String viewMatch(Model model) {
        return findPaginated(1, "matchStartDate", "asc", model);
    }

    @GetMapping("/updateMatches")
    public String updateMatch() throws JSONException {
        iMatchService.fillDB();
        return "redirect:/";
    }

    @GetMapping("/findOne/{id}")
    public Match getMatchById(@PathVariable(value = "id") Long id) {
        return iMatchService.getMatchById(id);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 10;

        Page<Match> page = iMatchService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Match> listMatches = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listMatches", listMatches);

        return "index";

    }
}
