package com.Friends.Bet.matches;

import com.Friends.Bet.matches.dao.Match;
import org.json.JSONException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMatchService {

    List<Match> findAll();

    Page<Match> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Match getMatchById(long id);

    void fillDB() throws JSONException;
}
