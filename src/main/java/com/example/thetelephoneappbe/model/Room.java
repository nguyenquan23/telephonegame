package com.example.thetelephoneappbe.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_room")
    private Long id;
    @Column(name = "status")
    private String status;
    @Column(name ="maxPlayer")
    private int maxPlayer = 4;

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public Room(){
    }

    public Room(String status){
        this.status = status;
    }

    @OneToMany(mappedBy = "room")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "room1")
    private List<Result> results = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "{" +
                "id = " + id +
                ", status = " + status +
                ", maxPlayer =" + maxPlayer +
                ", users = " + users +
                ", results = " + results +
                '}';
    }

//    @Override
//    public String toString() {
//        return "Room{" +
//                "id=" + id +
//                ", status='" + status + '\'' +
//                ", maxPlayer=" + maxPlayer +
//                ", users=" + users +
//                ", results=" + results +
//                '}';
//    }
}


