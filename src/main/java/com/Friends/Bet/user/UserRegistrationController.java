package com.Friends.Bet.user;

import com.Friends.Bet.user.dto.UserRegistrationDto;
import com.Friends.Bet.user.errors.UsernameAlreadyExist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserRegistrationController {

    private IUserService userService;

    public UserRegistrationController(IUserService userService) {

        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @GetMapping("/allUsers")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {

        if (userService.checkIfUserExist(userRegistrationDto.getEmail())) {
            return "redirect:/registration?error";
        }

        if (!userRegistrationDto.getConfirmPassword().equals(userRegistrationDto.getPassword())) {
            return "redirect:/registration?passwordConfirmError";
        }

        userService.save(userRegistrationDto);
        return "redirect:/";
    }
}