package com.murar.journel.controller;

import com.murar.journel.entity.JournalEntry;
import com.murar.journel.entity.User;
import com.murar.journel.service.JournalEntryService;
import com.murar.journel.service.UserEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Special type of component that handles HTTP requests
 */
@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userService;

    // Get all journal entries
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findUser(authentication.getName());

        if (user == null || user.getJournalEntryList() == null || user.getJournalEntryList().isEmpty()) {
            return new ResponseEntity<>("No Journel Entries", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(user.getJournalEntryList());
    }


    // Create new entry
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry, authentication.getName());
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating journal entry for user: {}", authentication.getName(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get entry by ID
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User byGivenUser = userService.findUser(username);

        boolean hasAccess = byGivenUser.getJournalEntryList()
                .stream()
                .anyMatch(x -> x.getId().equals(myId));

        if (hasAccess) {
            return journalEntryService.findByID(myId)
                    .map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    // Delete entry by ID
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<String> deleteEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (journalEntryService.deleteEntryById(username, myId)) {
            return new ResponseEntity<>(myId.toHexString() + " is deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error in the code", HttpStatus.NOT_FOUND);
        }

    }

    //     Update entry by ID
    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User byGivenUser = userService.findUser(username);

        // Ensure the entry belongs to the logged-in user
        JournalEntry oldEntry = byGivenUser.getJournalEntryList()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .findFirst()
                .orElse(null);

        if (oldEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update fields only if they are provided
        if (!newEntry.getTitle().isEmpty()) {
            oldEntry.setTitle(newEntry.getTitle());
        }
        if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
            oldEntry.setContent(newEntry.getContent());
        }

        journalEntryService.saveEntry(oldEntry);

        return new ResponseEntity<>(oldEntry, HttpStatus.OK);
    }
}

