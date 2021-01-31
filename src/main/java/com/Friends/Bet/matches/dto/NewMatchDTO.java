package com.Friends.Bet.matches.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewMatchDTO {

    private String host;
    private String guest;
    private LocalDateTime matchStartDate;
}
