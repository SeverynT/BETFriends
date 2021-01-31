package com.Friends.Bet.matches;

import com.Friends.Bet.informationDownloader.Data.DataMapper;
import com.Friends.Bet.informationDownloader.MatchInfo;
import com.Friends.Bet.matches.dao.Match;
import com.Friends.Bet.matches.dao.MatchRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.Friends.Bet.results.MatchResult.NOT_SET;

@Service
public class MatchService implements IMatchService {

    @Autowired
    private MatchRepository matchRepository;

    DataMapper dataMapper = new DataMapper();

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Optional<Match> findById(Long id) {
        return matchRepository.findById(id);
    }


    @Override
    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    @Override
    public Page<Match> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Clock cl = Clock.systemUTC();
        LocalDateTime dateTime = LocalDateTime.now(cl);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.matchRepository.findAllByResultAndMatchStartDateAfter(NOT_SET, dateTime, pageable);
    }


    public void deleteById(Long id) {
        matchRepository.deleteById(id);
    }

    public Match save(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public Match getMatchById(long id) {
        Optional<Match> optional = matchRepository.findById(id);
        Match match = null;
        if (optional.isPresent()) {
            match = optional.get();
        } else {
            throw new RuntimeException("Bet not found");
        }
        return match;
    }

    @Override
    public void fillDB() throws JSONException {
        List<MatchInfo> matchInfo = dataMapper.downloadAllRounds();
        for (int i = 0; i < matchInfo.size(); i++) {
            MatchInfo matchInfo1 = matchInfo.get(i);
            save(new Match((long) i, matchInfo1.getHostName(), matchInfo1.getGuestName(), matchInfo1.getFixtureId(), matchInfo1.getMatchStartDate(), matchInfo1.getResult(), matchInfo1.getHostGoals(), matchInfo1.getGuestGoals()));
        }
    }

}
