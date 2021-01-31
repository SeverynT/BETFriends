package com.Friends.Bet.user;

import com.Friends.Bet.user.dao.User;
import com.Friends.Bet.user.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);

    boolean checkIfUserExist(String email);

    List<User> findAll();

    User getUser();
}
