package com.murar.journel.service;

import com.murar.journel.entity.User;
import com.murar.journel.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(user);
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

}
