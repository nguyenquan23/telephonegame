package com.example.thetelephoneappbe.controller;

import com.example.thetelephoneappbe.DTO.ResultDTO;
import com.example.thetelephoneappbe.model.Room;
import com.example.thetelephoneappbe.model.User;
import com.example.thetelephoneappbe.service.RoleService;
import com.example.thetelephoneappbe.service.RoomService;
import com.example.thetelephoneappbe.service.UserService;
import com.example.thetelephoneappbe.storage.StorageGame;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private UserService userService;
    private RoomService roomService;
    private RoleService roleService;


    List<StorageGame> storageGames = new ArrayList<>();


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    Gson gson = new Gson();

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @PostMapping("/create/{user_name}")
    public ResponseEntity<String> create(@PathVariable("user_name") String userName) {
        User user = userService.creatUser(userName, roomService, roleService);
        return ResponseEntity.ok(gson.toJson(gson.fromJson(user.toString(), Object.class)));
    }


    @PostMapping("/join/{id_room}/{user_name}")
    public ResponseEntity<String> join(@PathVariable("id_room") Long idRoom, @PathVariable("user_name") String userName) {
        userService.joinUser(idRoom, userName, roomService, roleService);
        List<User> users = roomService.getOneRoom(idRoom).getUsers();
        simpMessagingTemplate.convertAndSend("/topic/" + idRoom, gson.toJson(gson.fromJson(users.toString(), Object.class)));
        return ResponseEntity.ok(gson.toJson(gson.fromJson(users.toString(), Object.class)));
    }

    @PostMapping("/delete/{id_room}/{nickname}")
    public ResponseEntity<String> deleteUserFromRoom(@PathVariable("id_room") Long roomId, @PathVariable("nickname") String name) {
        Room playRoom = roomService.getOneRoom(roomId);
        User userToDelete = playRoom
                .getUsers().stream()
                .filter(user -> user.getNickname()
                        .equals(name)).findFirst().orElseThrow();

        playRoom.getUsers().remove(userToDelete);
        roomService.SaveRoom(playRoom);
        userToDelete.setRoom(null);
        userService.saveUser(userToDelete);

        simpMessagingTemplate.convertAndSend(
                "/topic/" + roomId,
                gson.toJson(gson.fromJson(playRoom.getUsers().toString(), Object.class)));
        simpMessagingTemplate.convertAndSend(
                "/topic/" + userToDelete.getNickname(),
                gson.toJson(gson.fromJson(userToDelete.getNickname(), Object.class)));

        return ResponseEntity.ok(gson.toJson(gson.fromJson(playRoom.getUsers().toString(), Object.class)));
    }

    @PostMapping("/start/{id_room}")
    public ResponseEntity<String> start(@PathVariable("id_room") Long idRoom) {

        Room room = roomService.getOneRoom(idRoom);
        room.setStatus("IN_PROGRESS");
        List<User> users = room.getUsers();
        StorageGame storageGame = new StorageGame();
        room.getUsers().stream().forEach(user -> storageGame.getResult().put(user.getNickname(), new LinkedHashMap<>()));
        storageGame.setIdRoom(idRoom);
        storageGames.add(storageGame);
        simpMessagingTemplate.convertAndSend("/topic/" + idRoom, gson.toJson(gson.fromJson(users.toString(), Object.class)));
        return ResponseEntity.ok(gson.toJson(gson.fromJson(users.toString(), Object.class)));
    }

    @PostMapping("/done/{id_room}/{name}/{data}/{turn}")
    public ResponseEntity<String> Done(@PathVariable("id_room") Long idRoom, @PathVariable("name") String nickname, @PathVariable("data") String data, @PathVariable("turn") Integer turn) {

        Room playRoom = roomService.getOneRoom(idRoom);


        StorageGame storageGamePlay = storageGames.stream()
                .filter(storageGame -> storageGame.getIdRoom().equals(idRoom))
                .reduce((first, second) -> second).orElseThrow();

        if (storageGamePlay.getResult().containsKey(nickname)) {
            Map<Integer, String> innerMap = storageGamePlay.getResult().get(nickname);
            innerMap.put(turn, data);
        }

        storageGamePlay.getNicknames().add(nickname);
        boolean areListsEqual = playRoom.getUsers().size() == storageGamePlay.getNicknames().size();
        if (areListsEqual) {
            storageGamePlay.setKeyNickName(new ArrayList<>(storageGamePlay.getResult().keySet()));
            for (Map<Integer, String> map : storageGamePlay.getResult().values()) {
                LinkedHashMap<Integer, String> subMap = new LinkedHashMap<>(map);
                if (subMap.containsKey(turn)) {
                    storageGamePlay.getValues().add(subMap.get(turn));
                }
            }
            Collections.rotate(storageGamePlay.getValues(), turn);
            storageGamePlay.getReceiver().addAll(storageGamePlay.getKeyNickName());
            Collections.rotate(storageGamePlay.getReceiver(), turn);
            if (turn % 2 == 0) {
                playRoom.setStatus("DRAW");
            } else {
                playRoom.setStatus("WRITE");
            }
            for (int i = 0; i < playRoom.getUsers().size(); i++) {
                String JSON = "{value:" + storageGamePlay.getValues().get(i) + ", receiver:" + storageGamePlay.getReceiver().get(i) + ", status:" + playRoom.getStatus() + ", number:" + playRoom.getUsers().size() + "}";
                simpMessagingTemplate.convertAndSend("/topic/" + storageGamePlay.getKeyNickName().get(i), gson.toJson(gson.fromJson(JSON, Object.class)));
            }

            storageGamePlay.setNicknames(new HashSet<>());
            storageGamePlay.setReceiver(new ArrayList<>());
            storageGamePlay.setKeyNickName(new ArrayList<>());
            storageGamePlay.setValues(new ArrayList<>());

        }
        return ResponseEntity.ok(gson.toJson(gson.fromJson(playRoom.getUsers().toString(), Object.class)));
    }

    @PostMapping("/result/{nickname}/{id_room}")
    public ResponseEntity<String> result(@PathVariable("nickname") String nickname, @PathVariable("id_room") Long idRoom) {
        StorageGame storageGamePlay = storageGames.stream()
                .filter(storageGame -> storageGame.getIdRoom().equals(idRoom))
                .reduce((first, second) -> second).orElseThrow();
        int count = 0;
        List<ResultDTO> resultDTOs = new ArrayList<>();
        List<String> nicks = new ArrayList<>(storageGamePlay.getResult().keySet());
        Collections.rotate(nicks, nicks.indexOf(nickname));
        Map<Integer, String> data = storageGamePlay.getResult().get(nickname);
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
            ResultDTO resultDTO = new ResultDTO(nicks.get(count++), entry.getValue());
            resultDTOs.add(resultDTO);
        }
        for (String nick : nicks) {
            simpMessagingTemplate.convertAndSend("/topic/" + nick, gson.toJson(gson.fromJson(resultDTOs.toString(), Object.class)));
        }
        return ResponseEntity.ok(gson.toJson(gson.fromJson(resultDTOs.toString(), Object.class)));
    }
    @PostMapping("/again/{id_room}")
    public ResponseEntity<String> playAgain(@PathVariable("id_room") Long idRoom){
        Room playRoom = roomService.getOneRoom(idRoom);
        playRoom.setStatus("AGAIN");
        simpMessagingTemplate.convertAndSend("/topic/" + idRoom, gson.toJson(gson.fromJson(playRoom.getUsers().toString(), Object.class)));
        return ResponseEntity.ok("again");

    }



}
