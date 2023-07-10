package com.example.thetelephoneappbe.service.impl;

import com.example.thetelephoneappbe.model.Role;

import com.example.thetelephoneappbe.model.Room;
import com.example.thetelephoneappbe.model.User;
import com.example.thetelephoneappbe.repository.UserRepository;
import com.example.thetelephoneappbe.service.RoleService;
import com.example.thetelephoneappbe.service.RoomService;
import com.example.thetelephoneappbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.thetelephoneappbe.enums.ERole.ROLE_HOST;
import static com.example.thetelephoneappbe.enums.ERole.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User creatUser(String userName, RoomService roomService, RoleService roleService) {
        User user = new User();
        user.setNickname(userName);
        user.setRoom(roomService.SaveRoom(new Room("NEW")));
        Role role = roleService.getAllRole().stream().filter(role1 -> role1.getName().equals(ROLE_HOST)).toList().get(0);
        user.getRoles().add(role);
        role.getUsers().add(user);
        return userRepository.save(user);
    }

    @Override
    public void joinUser(Long idRoom, String userName, RoomService roomService, RoleService roleService) {
        User user = new User();
        user.setNickname(userName);
        Room room = roomService.getAllRoom().stream().filter(room1 -> room1.getId().equals(idRoom)).toList().get(0);
        user.setRoom(room);
        Role role = roleService.getAllRole().stream().filter(role1 -> role1.getName().equals(ROLE_USER)).toList().get(0);
        user.getRoles().add(role);
        role.getUsers().add(user);
        userRepository.save(user);
    }

    @Override
    public List<User> getUserByIdRoom(Long idRoom) {
        System.out.println(userRepository.findAll());
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRoom().getId().equals(idRoom))
                .toList();
    }

    @Override
    public User saveUser(User user){
        return  userRepository.save(user);
    }
}
