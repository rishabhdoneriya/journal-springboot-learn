package com.murar.journel.repository;


import com.murar.journel.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// This mongo repository interface is provided by Spring Mongo , for our convenience, Now service
public interface UserEntryRepository extends MongoRepository<User, ObjectId> {

    User findByUsername(String username);
    User deleteByUsername(String username);

}
