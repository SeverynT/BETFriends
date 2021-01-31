package com.Friends.Bet.informationDownloader.Data;

import com.Friends.Bet.informationDownloader.Errors.ExternalError;
import com.Friends.Bet.informationDownloader.MatchInfo;
import com.Friends.Bet.results.MatchResult;
import io.vavr.control.Either;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import io.vavr.control.Option;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataMapper {

    DataDownloader downloader = new DataDownloader();

    public List<MatchInfo> downloadAllRounds() throws JSONException {
        Either<ExternalError, JSONArray> fixtures = downloader.downloadAllRounds();
        return getMatchInfosFromJsonArray(fixtures);
    }


    List<MatchInfo> getMatchInfosFromJsonArray(Either<ExternalError, JSONArray> fixtures) throws JSONException {
        List<MatchInfo> matchInfos = new ArrayList<>();

        for (int i = 0; i < fixtures.get().length(); i++) {
            JSONObject fixture = fixtures.get().getJSONObject(i);
            Option<MatchInfo> matchInfo = createMatchInfo(fixture);

            if (matchInfo.isDefined()) {
                matchInfos.add(matchInfo.get());
            }
        }

        return matchInfos;
    }

    private Option<MatchInfo> createMatchInfo(JSONObject fixture) throws JSONException {
        String homeTeamName = fixture.getJSONObject("homeTeam").getString("team_name");
        String awayTeamName = fixture.getJSONObject("awayTeam").getString("team_name");
        int matchFixtureId = fixture.getInt("fixture_id");


        String status = fixture.getString("status");
        boolean finished = status.equals("Match Finished");

        LocalDateTime startDate = getDateFromFixtureJSONObject(fixture);

        MatchResult result = MatchResult.NOT_SET;

//        if match isn't ended variable goalsHomeTeam and goalsAwayTeam have value 0
        int goalsHomeTeam = 0;
        int goalsAwayTeam = 0;


        if (finished) {
            goalsHomeTeam = fixture.getInt("goalsHomeTeam");
            goalsAwayTeam = fixture.getInt("goalsAwayTeam");
            result = MatchResult.fromResult(goalsHomeTeam, goalsAwayTeam);
        }

        if (homeTeamName.equals("") || awayTeamName.equals("")) {
            return Option.none();
        }

        MatchInfo matchInfo = new MatchInfo(homeTeamName, awayTeamName, matchFixtureId, startDate, finished, result, goalsHomeTeam, goalsAwayTeam);
        return Option.of(matchInfo);
    }

    private LocalDateTime getDateFromFixtureJSONObject(JSONObject fixture) throws JSONException {
        return ZonedDateTime.parse(fixture.getString("event_date")).toLocalDateTime();
    }
}
