package com.Friends.Bet.points;

import com.Friends.Bet.bet.dao.Bet;
import com.Friends.Bet.points.dao.Point;
import com.Friends.Bet.points.dao.PointRepository;
import com.Friends.Bet.results.BetResult;
import com.Friends.Bet.user.dao.User;
import com.Friends.Bet.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PointService implements IPointService {

    @Autowired
    PointRepository pointRepository;

    @Autowired
    IUserService iUserService;

    @Override
    public void pointSettlementOfTheBet(Bet bet) {
        Optional<Point> optional = pointRepository.findUsersPoints(bet.getUser(), bet.getOpponent());
        Point point = null;

        if (optional.isPresent()) {
            point = optional.get();

            int points = point.getPoints();
            int friendPoints = point.getFriendPoints();

            if (point.getUser().getId() == bet.getUser().getId()) {

                if (bet.getBetResult() == BetResult.USER_WIN) {
                    points += 5;
                    friendPoints -= 2;
                    point.setPoints(points);
                    point.setFriendPoints(friendPoints);

                } else if (bet.getBetResult() == BetResult.OPPONENT_WIN) {
                    points -= 2;
                    friendPoints += 5;
                    point.setPoints(points);
                    point.setFriendPoints(friendPoints);
                }

            } else {

                if (bet.getBetResult() == BetResult.USER_WIN) {
                    points -= 2;
                    friendPoints += 5;
                    point.setPoints(points);
                    point.setFriendPoints(friendPoints);

                } else if (bet.getBetResult() == BetResult.OPPONENT_WIN) {
                    points += 5;
                    friendPoints -= 2;
                    point.setPoints(points);
                    point.setFriendPoints(friendPoints);
                }

            }

            pointRepository.save(point);

            //Initial a new fight between two users
        } else {

            Point point1 = new Point();
            point1.setUser(bet.getUser());
            point1.setFriend(bet.getOpponent());

            if (bet.getBetResult() == BetResult.USER_WIN) {
                point1.setPoints(5);
                point1.setFriendPoints(-2);

            } else if (bet.getBetResult() == BetResult.OPPONENT_WIN) {
                point1.setPoints(-2);
                point1.setFriendPoints(5);
            }

            pointRepository.save(point1);
        }
    }

    @Override
    public Page<Point> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        User user = iUserService.getUser();

        return this.pointRepository.findAllByUserOrFriend(user, user, pageable);
    }
}
