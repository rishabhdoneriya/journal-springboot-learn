package com.murar.journel.entity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="config_journal")
public class ConfigJournalEntity {


    @Id
    private ObjectId id;
    private String key;

    private String value;
    
}
