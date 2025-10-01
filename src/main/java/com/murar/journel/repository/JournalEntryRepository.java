package com.murar.journel.repository;


import com.murar.journel.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// This mongo repository interface is provided by Spring Mongo , for our convienient, Now service
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {



}
