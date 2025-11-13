package com.murar.journel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(JournalApplication.class, args);
       log.info(Arrays.toString(context.getEnvironment().getActiveProfiles()));

	}
	// MongoDB Transaction Manager is the interface that will be used to manage the transactions. It is implemented by the MongoTransactionManager class.
	
	//transaction related kaam . It'll help us to rollback the transaction if any error occurs. also helps to maintain the consistency of the data.
	//This is a bean that will be used to manage the transactions.
	@SuppressWarnings("null")
	@Bean
	public PlatformTransactionManager give(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

    @Bean
    public RestTemplate initiateRestTemplate(){
        return new RestTemplate();
    }

}
