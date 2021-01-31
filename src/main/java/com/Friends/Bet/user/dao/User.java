package com.Friends.Bet.user.dao;

import com.Friends.Bet.bet.dao.Bet;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private int point;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Bet> betsAsUser;

    @OneToMany(mappedBy = "opponent")
    private List<Bet> betsAsOpponent;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, int point, Collection<Role> roles, List<Bet> betsAsUser, List<Bet> betsAsOpponent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.point = point;
        this.roles = roles;
        this.betsAsUser = betsAsUser;
        this.betsAsOpponent = betsAsOpponent;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Bet> getBetsAsUser() {
        return betsAsUser;
    }

    public void setBetsAsUser(List<Bet> betsAsUser) {
        this.betsAsUser = betsAsUser;
    }

    public List<Bet> getBetsAsOpponent() {
        return betsAsOpponent;
    }

    public void setBetsAsOpponent(List<Bet> betsAsOpponent) {
        this.betsAsOpponent = betsAsOpponent;
    }
}
