package com.murar.journel.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.murar.journel.entity.ConfigJournalEntity;


public interface ConfigJournalRepository extends MongoRepository<ConfigJournalEntity, ObjectId> {
    
    
    
}
