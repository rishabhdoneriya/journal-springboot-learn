package com.murar.journel.controller;

import com.murar.journel.entity.User;
import com.murar.journel.service.UserEntryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;


    @PostMapping("create-user")
    public User createNewUser(@RequestBody User user){
        userEntryService.saveNewEntry(user);
        return user;

    }
    @GetMapping("/health-check")
    public ResponseEntity<?> createNewUser(){
       return new ResponseEntity<>(HttpStatus.OK);

    }

}
