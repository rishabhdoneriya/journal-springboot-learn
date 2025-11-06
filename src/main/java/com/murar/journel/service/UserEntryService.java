package com.murar.journel.service;

import com.murar.journel.entity.User;
import com.murar.journel.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserEntryService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void saveEntry(User user){
        userEntryRepository.save(user);
    }

    public void saveNewEntry(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userEntryRepository.save(user);
        } catch (Exception e) {
            log.error(  "User cant be created");
            throw new RuntimeException(e);
        }
    }




    public List<User> getAll(){
        return userEntryRepository.findAll();
    }


    public Optional<User> findByID(ObjectId id){
        return userEntryRepository.findById(id);

    }

    public void deleteEntryById(ObjectId id){
        userEntryRepository.deleteById(id);
    }

    public void deleteEntryByUsername(String username){
        userEntryRepository.delete(userEntryRepository.findByUsername(username));
    }



    public User findUser(String username){
        return userEntryRepository.findByUsername(username);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ADMIN","USER"));
        userEntryRepository.save(user);

    }
}
