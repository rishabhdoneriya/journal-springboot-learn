package com.murar.journel.controller;

import com.murar.journel.entity.User;
import com.murar.journel.repository.UserEntryRepository;
import com.murar.journel.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;


    @PostMapping("/create")
    public User createNewUser(@RequestBody User user){
        userEntryService.saveEntry(user);
        return user;

    }
    @GetMapping("/health-check")
    public ResponseEntity<?> createNewUser(){
       return new ResponseEntity<>(HttpStatus.OK);

    }

}
