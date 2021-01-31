package com.Friends.Bet.points;

import com.Friends.Bet.bet.dao.Bet;
import com.Friends.Bet.points.dao.Point;
import org.springframework.data.domain.Page;

public interface IPointService {
    void pointSettlementOfTheBet(Bet bet);

    Page<Point> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
