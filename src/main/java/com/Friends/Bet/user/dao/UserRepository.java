package com.Friends.Bet.user.dao;

import com.Friends.Bet.user.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id NOT IN :usr")
    List<User> findAllOpponent(@Param("usr") Set<Long> opponentId);

}
