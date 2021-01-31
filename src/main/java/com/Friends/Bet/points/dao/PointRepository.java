package com.Friends.Bet.points.dao;

import com.Friends.Bet.user.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("SELECT p FROM Point p WHERE (p.user = ?1 AND p.friend = ?2) OR (p.user = ?2 AND p.friend = ?1)")
    Optional<Point> findUsersPoints(User user, User friend);

    Page<Point> findAllByUserOrFriend(User user, User friend, Pageable pageable);
}
