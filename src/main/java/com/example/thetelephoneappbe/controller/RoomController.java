package com.example.thetelephoneappbe.controller;

import com.example.thetelephoneappbe.service.RoleService;
import com.example.thetelephoneappbe.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class RoomController {
    private RoomService roomService;
    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }
}
