// com.murar.journel.WeatherStackResponse
package com.murar.journel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherStackResponse {
    private Current current;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        private Integer temperature;   // °C
        private Integer feelslike;     // °C
        private Integer humidity;    
        @JsonProperty("uv_index")  // %
        private Integer uvIndex;
        private Integer visibility;
    }
}
