package com.example.demo.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonObjectMapperConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper =  new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;

    }
}
