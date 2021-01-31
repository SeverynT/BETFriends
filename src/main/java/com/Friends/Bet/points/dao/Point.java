package com.Friends.Bet.points.dao;

import com.Friends.Bet.user.dao.User;

import javax.persistence.*;

@Entity
@Table(name = "point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "points")
    private int points;

    @Column(name = "friend_points")
    private int friendPoints;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;


    public Point() {
    }

    public Point(Long id, int points, int friendPoints, User user, User friend) {
        this.id = id;
        this.points = points;
        this.friendPoints = friendPoints;
        this.user = user;
        this.friend = friend;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getFriendPoints() {
        return friendPoints;
    }

    public void setFriendPoints(int friendPoints) {
        this.friendPoints = friendPoints;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
