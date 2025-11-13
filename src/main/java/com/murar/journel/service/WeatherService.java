package com.murar.journel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.murar.journel.Constants.PlaceHolder;
import com.murar.journel.appcache.*;

import com.murar.journel.entity.WeatherStackResponse;

@Slf4j
@Service
public class WeatherService {
    @Value("${weather.api-key}")
    private String api;

    @Autowired
    private AppCache applicationCache;

    @Autowired
    private RestTemplate restTemplate;


    @SuppressWarnings("null")
    public String getCurrentWeather(String city) {
        String url = applicationCache.appCacheVariable.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolder.API_KEYS, api).replace(PlaceHolder.CITY, city);

        // Make the API call and get the full response
        ResponseEntity<WeatherStackResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                WeatherStackResponse.class
        );

        // Check HTTP status
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            try {
                WeatherStackResponse weatherStackResponse = response.getBody();
                return "Feels like " + weatherStackResponse.getCurrent().getFeelslike();
            } catch (NullPointerException e) {
                log.error("NullPointerException while parsing weather response for city: {}", city, e);
                throw new RuntimeException("Get Body returned null", e);
            }
        } else {
            log.warn("Failed to fetch weather data for city: {}. Status: {}", city, response.getStatusCode());
            return "Failed to fetch weather data. Status: " + response.getStatusCode();
        }
    }




}

