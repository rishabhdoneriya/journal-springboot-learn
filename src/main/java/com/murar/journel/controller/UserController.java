package com.murar.journel.controller;

import com.murar.journel.entity.User;
import com.murar.journel.repository.UserEntryRepository;
import com.murar.journel.service.UserEntryService;
import com.murar.journel.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserEntryService userService;

    @Autowired
    private UserEntryRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public List<User> getAll(){
        log.debug("Fetching all users"); // Debug level
        List<User> users = userService.getAll();
        log.info("Retrieved {} users", users.size()); // Info level
        return users;
    }




    //iski help se db mei username aur password badal skte h .. 
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        log.info("Updating user: {}", authentication.getName()); // Info level
        User userInDB= userService.findUser(authentication.getName());
       if(userInDB!=null){
           log.debug("User found, updating details"); // Debug level
           userInDB.setUsername(user.getUsername());
           userInDB.setPassword(user.getPassword());
       } else {
           log.warn("User not found for update: {}", authentication.getName()); // Warning level
       }
       userService.saveNewEntry(userInDB);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUserByUsername(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
       try
       {
           userRepository.deleteByUsername(authentication.getName());
       } catch (Exception e){
           log.error("User Not Found: {}", authentication.getName(), e);
       }


        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping("/get-weather")
    public ResponseEntity<?> getWeather(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        log.info("Fetching weather for user: {}", authentication.getName()); // Info level

        String weatherServiceResponse = weatherService.getCurrentWeather("morena");
        if(weatherServiceResponse.isEmpty()){
            log.warn("Weather service returned empty response"); // Warning level
            return new ResponseEntity<>("NULL",HttpStatus.BAD_GATEWAY);
        }else{
            String username =  authentication.getName();
            String response = "HI "+username+", The weather" + weatherServiceResponse;
            log.debug("Weather response: {}", weatherServiceResponse); // Debug level
            return new ResponseEntity<>(response,HttpStatus.OK);
        }

    }




}
