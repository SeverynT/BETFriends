package com.Friends.Bet.informationDownloader.Data;

import com.Friends.Bet.informationDownloader.Errors.ExternalError;
//import com.Friends.Bet.Api.ExternalSourceConfiguration;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class DataDownloader {

    private static final String URL_BEGINNING = "https://api-football-v1.p.rapidapi.com/v2/fixtures/league/2790";

    private static final String TOKEN = "TOKEN";

    private static final Map<String, String> headers = new HashMap<>();

    private static final Logger LOGGER = Logger.getLogger(DataDownloader.class.getName());

    static {
        headers.put("x-rapidapi-key", TOKEN);
        headers.put("x-rapidapi-host", "api-football-v1.p.rapidapi.com");
    }

    public Either<ExternalError, JSONArray> downloadAllRounds() {
        int leagueId = 4;
        String url = URL_BEGINNING;
        return getMatches(url);
    }

    private Either<ExternalError, JSONArray> getMatches(String url) {
        return Try.of(() -> getFixturesAsJsonArray(url))
                .onFailure(throwable -> LOGGER.log(Level.SEVERE, throwable.getMessage()))
                .toEither()
                .mapLeft(x -> ExternalError.ERROR_DURING_DOWNLOADING);
    }

    private JSONArray getFixturesAsJsonArray(String url) throws UnirestException, JSONException {

        return Unirest.get(url)
                .headers(headers)
                .asJson()
                .getBody()
                .getObject()
                .getJSONObject("api")
                .getJSONArray("fixtures");
    }


}
