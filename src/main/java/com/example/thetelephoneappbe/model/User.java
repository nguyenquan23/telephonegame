package com.example.thetelephoneappbe.model;

import javax.persistence.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    @Column(name = "nickname",unique = true)
    private String nickname;
    @Column(name = "image")
    private String id_image;

    private String modeCurrent;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_room", referencedColumnName = "id_room")
    Room room = new Room();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id_role"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user1")
    private List<Result> results = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getModeCurrent() {
        return modeCurrent;
    }

    public void setModeCurrent(String modeCurrent) {
        this.modeCurrent = modeCurrent;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getId_image() {
        return id_image;
    }

    public void setId_image(String id_image) {
        this.id_image = id_image;
    }

    @Override
    public String toString() {
        return  "{" +
                "  id:" + id +
                ", nickname:" + nickname +
                ", id_image:" + id_image +
                ", role:" + roles +
                ", maxPlayer:" + room.getMaxPlayer() +
                ", modeCurrent:" + modeCurrent +
                ", id_room:" + room.getId() +
                ", status:" + room.getStatus() +
                "}";
    }
}

