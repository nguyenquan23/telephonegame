package com.example.thetelephoneappbe.service;

import com.example.thetelephoneappbe.model.Room;

import java.util.List;

public interface RoomService {
    Room SaveRoom(Room room);
    List<Room> getAllRoom();
    Room getOneRoom(Long id);


}
