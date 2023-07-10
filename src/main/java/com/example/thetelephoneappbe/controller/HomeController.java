package com.example.thetelephoneappbe.controller;


import com.example.thetelephoneappbe.model.Role;
import com.example.thetelephoneappbe.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.HttpStatus;


import java.util.List;

@Controller

@RequestMapping("/home")
public class HomeController {
    private RoleService roleService;
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Role>> getAll(){
        System.out.println(roleService.getAllRole());
        return new ResponseEntity<>(roleService.getAllRole(), HttpStatus.OK);

    }

    @GetMapping("/start")
    public ResponseEntity<List<Role>> start() {
        System.out.println("start function");
        return ResponseEntity.ok(roleService.getAllRole());
    }

}
