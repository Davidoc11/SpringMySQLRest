package com.example.SpringMySQLRest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author David
 */
//@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
       // mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
