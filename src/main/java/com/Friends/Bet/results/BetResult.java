package com.Friends.Bet.results;

public enum BetResult {
    USER_WIN, OPPONENT_WIN, NOBODY_WON;

    public static BetResult fromBet(MatchResult matchResult, boolean typ) {

        if (matchResult != MatchResult.NOT_SET) {
            if ((matchResult == MatchResult.HOST_WON && !typ) || (matchResult == MatchResult.GUEST_WON && typ)) {
                return BetResult.USER_WIN;
            } else if (matchResult == MatchResult.DRAW) {
                return BetResult.NOBODY_WON;
            } else {
                return BetResult.OPPONENT_WIN;
            }
        }
        return null;
    }
}
