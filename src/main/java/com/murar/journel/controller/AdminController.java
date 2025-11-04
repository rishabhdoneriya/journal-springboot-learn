package com.murar.journel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.murar.journel.entity.User;
import com.murar.journel.service.UserEntryService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userEntryService.getAll();
        if(users.isEmpty()) {
            return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        user.setRoles(List.of(new String[]{"ADMIN"}));
        userEntryService.saveAdmin(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);

    }


    
}
