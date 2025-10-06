package com.murar.journel.controller;

import com.murar.journel.entity.User;
import com.murar.journel.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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




    //iski help se db mei username aur password badal skte h .. 
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
       User userInDB= userService.findUser(authentication.getName());
       if(userInDB!=null){
           userInDB.setUsername(user.getUsername());
           userInDB.setPassword(user.getPassword());
       }
       userService.saveEntry(userInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






}
