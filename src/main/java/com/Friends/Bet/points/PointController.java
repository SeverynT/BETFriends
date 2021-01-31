package com.Friends.Bet.points;

import com.Friends.Bet.points.dao.Point;
import com.Friends.Bet.user.dao.User;
import com.Friends.Bet.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/points")
public class PointController {

    @Autowired
    IPointService iPointService;

    @Autowired
    IUserService iUserService;

    @GetMapping
    public String showPointStat(Model model) {
        return findPaginated(1, "points", "asc", model);
    }

    @GetMapping("/pointPage/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Point> page = iPointService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Point> statPoints = page.getContent();

        User user = iUserService.getUser();


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("currentUser", user);

        model.addAttribute("statPoints", statPoints);

        return "user_points";


    }

}
