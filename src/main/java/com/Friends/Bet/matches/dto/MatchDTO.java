package com.Friends.Bet.matches.dto;

import com.Friends.Bet.results.MatchResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {

    private UUID uuid;
    private LocalDateTime matchStartDate;
    private String host;
    private String guest;
    private MatchResult result;

    public static MatchDTO fromNewMatchDTO(NewMatchDTO newMatchDTO) {
        return new MatchDTO(UUID.randomUUID(),
                newMatchDTO.getMatchStartDate(),
                newMatchDTO.getHost(),
                newMatchDTO.getGuest(),
                MatchResult.NOT_SET);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchDTO matchDTO = (MatchDTO) o;
        return uuid == matchDTO.uuid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, host, guest, result);
    }

}
