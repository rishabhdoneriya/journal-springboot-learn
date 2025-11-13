package com.murar.journel.appcache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.murar.journel.entity.ConfigJournalEntity;
import com.murar.journel.repository.ConfigJournalRepository;

import jakarta.annotation.PostConstruct;


@Component
@Slf4j
public class AppCache {

    @Autowired
    private ConfigJournalRepository configJournalRepository;

    public enum keys{
        WEATHER_API;
    }



    public  Map<String,String> appCacheVariable ;

    @PostConstruct
    public void init(){
        appCacheVariable = new HashMap<>();
        List<ConfigJournalEntity> all = configJournalRepository.findAll();
        for (ConfigJournalEntity configJournalAppEntity : all) {
            appCacheVariable.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

}
    



    
