package com.Friends.Bet.informationDownloader;

import com.Friends.Bet.results.MatchResult;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;


@AllArgsConstructor
public class MatchInfo {

    private final String hostName;
    private final String guestName;
    private final int fixtureId;
    private final LocalDateTime matchStartDate;
    private final boolean finished;
    private final MatchResult result;
    private final int hostGoals;
    private final int guestGoals;


    public String getHostName() {
        return hostName;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getFixtureId() {
        return fixtureId;
    }

    public boolean isFinished() {
        return finished;
    }

    public MatchResult getResult() {
        return result;
    }

    public LocalDateTime getMatchStartDate() {
        return matchStartDate;
    }

    public int getHostGoals() {
        return hostGoals;
    }

    public int getGuestGoals() {
        return guestGoals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchInfo matchInfo = (MatchInfo) o;

        return finished == matchInfo.finished &&
                Objects.equals(hostName, matchInfo.hostName) &&
                Objects.equals(guestName, matchInfo.guestName) &&
                Objects.equals(fixtureId, matchInfo.fixtureId) &&
                Objects.equals(hostGoals, matchInfo.hostGoals) &&
                Objects.equals(guestGoals, matchInfo.guestGoals) &&
                result == matchInfo.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostName, guestName, fixtureId, finished, hostGoals, guestGoals, result);
    }


}
