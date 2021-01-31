package com.Friends.Bet.informationDownloader.Errors;

import com.Friends.Bet.informationDownloader.Errors.ResponseError;

public enum ExternalError implements ResponseError {

    ERROR_DURING_DOWNLOADING("Error during downloading", 500);

    private String message;
    private int httpCode;

    ExternalError(String message, int httpCode) {
        this.message = message;
        this.httpCode = httpCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }
}
