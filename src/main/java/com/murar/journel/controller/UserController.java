package com.murar.journel.controller;

import com.murar.journel.entity.User;
import com.murar.journel.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserEntryService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @PostMapping
    public User createUser(@RequestBody User user){
        userService.saveEntry(user);
        return user;
    }



    //iski help se db mei username aur password badal skte h .. 
    @PutMapping("{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
       User userInDB= userService.findUser(username);
       if(userInDB!=null){
           userInDB.setUsername(user.getUsername());
           userInDB.setPassword(user.getPassword());
       }
       userService.saveEntry(userInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






}
