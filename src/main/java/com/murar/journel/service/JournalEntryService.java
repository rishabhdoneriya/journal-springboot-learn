package com.murar.journel.service;

//controller calls for service and service to repository.
//Spring data Mongodb will provide a interface.

import com.murar.journel.entity.JournalEntry;
import com.murar.journel.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

}
