package com.example.thetelephoneappbe.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "playindex")
    private Long playIndex;

    @Column(name = "gameturn")
    private Long gameTurn;

    @Column(name = "information")
    private String information;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_room", referencedColumnName = "id_room")
    Room room1 = new Room();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    User user1 = new User();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayIndex() {
        return playIndex;
    }

    public void setPlayIndex(Long playIndex) {
        this.playIndex = playIndex;
    }

    public Long getGameTurn() {
        return gameTurn;
    }

    public void setGameTurn(Long gameTurn) {
        this.gameTurn = gameTurn;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

}
