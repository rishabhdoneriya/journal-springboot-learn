package com.murar.journel.controller;

import com.murar.journel.entity.JournalEntry;
import com.murar.journel.entity.User;
import com.murar.journel.service.JournalEntryService;
import com.murar.journel.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Special type of component that handles HTTP requests
 */
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userService;

    // Get all journal entries
    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
        User user = userService.findUser(username);

        if (user == null || user.getJournalEntryList() == null || user.getJournalEntryList().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(user.getJournalEntryList());
    }


    // Create new entry
    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username) {


        try {

            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get entry by ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findByID(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Delete entry by ID
    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<String> deleteEntryById(@PathVariable String username,@PathVariable ObjectId myId) {

        journalEntryService.deleteEntryById(username,myId);
        return new ResponseEntity<>(myId.toHexString() + " is deleted", HttpStatus.OK);
    }

//     Update entry by ID
    @PutMapping("/id/{username}/{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(
            @PathVariable String username,
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry)
    {
        JournalEntry oldEntry = journalEntryService.findByID(myId).orElse(null);


        if (oldEntry != null) {
            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                oldEntry.setTitle(newEntry.getTitle());
            }
            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                oldEntry.setContent(newEntry.getContent());
            }
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
