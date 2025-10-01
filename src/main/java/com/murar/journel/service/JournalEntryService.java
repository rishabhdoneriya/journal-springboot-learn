package com.murar.journel.service;

//controller calls for service and service to repository.
//Spring data Mongodb will provide  interface.

import com.murar.journel.entity.JournalEntry;
import com.murar.journel.entity.User;
import com.murar.journel.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserEntryService userService;

    public void saveEntry(JournalEntry journalEntry, String username){
        User user = userService.findUser(username);

        JournalEntry saved = journalEntryRepository.save(journalEntry);
        saved.setDate(LocalDateTime.now());
        user.getJournalEntryList().add(saved);
        userService.saveEntry(user);

    }
    public void saveEntry(JournalEntry journalEntry){


      journalEntryRepository.save(journalEntry);



    }


    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> findByID(ObjectId id){
        return journalEntryRepository.findById(id);

    }

    public void deleteEntryById(String username, ObjectId id){
        User user = userService.findUser(username);
        user.getJournalEntryList().removeIf(x->x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }



}
