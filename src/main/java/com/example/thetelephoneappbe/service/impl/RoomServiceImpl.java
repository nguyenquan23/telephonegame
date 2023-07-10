package com.example.thetelephoneappbe.service.impl;

import com.example.thetelephoneappbe.model.Room;
import com.example.thetelephoneappbe.repository.RoleRepository;
import com.example.thetelephoneappbe.repository.RoomRepository;
import com.example.thetelephoneappbe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @Override
    public Room SaveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Room getOneRoom(Long id) {
        return roomRepository.getById(id);
    }


}
