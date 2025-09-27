package com.murar.journel.controller;

import com.murar.journel.entity.JournalEntry;
import com.murar.journel.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Special Type of components jo ki handle karte h hmari http request ko
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;



    @GetMapping("/abc")
    public List<JournalEntry> getAll(){
        return null;
    }

    @PostMapping("/bcd")
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntryService.saveEntry(myEntry);
        return true;

    }

    //Path Variable Example
    @GetMapping("id/{myId}")
    public JournalEntry getEntryById(@PathVariable long myId){
        return null;

    }

    @DeleteMapping("id/{myId}")
    public String deleteEntryById(@PathVariable long myId){
        return "Delete";
    }

    @PutMapping("id/{myId}")
    public String updateEntryById(@PathVariable long myId,@RequestBody JournalEntry myEntry){
        return " Added ";
    }

}
