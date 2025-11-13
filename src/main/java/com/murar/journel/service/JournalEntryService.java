package com.murar.journel.service;

//controller calls for service and service to repository.
//Spring data Mongodb will provide  interface.

import com.murar.journel.entity.JournalEntry;
import com.murar.journel.entity.User;
import com.murar.journel.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserEntryService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try {
            User user = userService.findUser(username);
            journalEntry.setDate(LocalDateTime.now());

            JournalEntry saved = journalEntryRepository.save(journalEntry);

            user.getJournalEntryList().add(saved);
            userService.saveEntry(user);
        } catch (Exception e) {
            log.error("Error saving journal entry for user: {}", username, e);
            throw new RuntimeException("An error occoured while saving the entry", e);
        }

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

    @Transactional
    public boolean deleteEntryById(String username, ObjectId id){
        boolean removed = false;
        try {
          User user = userService.findUser(username);

          removed = user.getJournalEntryList().removeIf(x->x.getId().equals(id));
          if(removed){ userService.saveEntry(user);
              journalEntryRepository.deleteById(id);}
      } catch (Exception e) {
            log.error("Error deleting journal entry with id: {} for user: {}", id, username, e);
          throw new RuntimeException("Id can't be deleted", e);
      }

      return removed;
    }

    public List<JournalEntry> getByUsername(String username){
        return userService.findUser(username).getJournalEntryList();
    }



}
