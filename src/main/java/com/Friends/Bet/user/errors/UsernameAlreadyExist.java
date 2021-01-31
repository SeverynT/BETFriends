package com.Friends.Bet.user.errors;

import org.springframework.security.core.AuthenticationException;

public class UsernameAlreadyExist extends AuthenticationException {

    public UsernameAlreadyExist(String msg) {
        super(msg);
    }
}
