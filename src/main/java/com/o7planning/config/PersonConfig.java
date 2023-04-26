package com.o7planning.config;
import com.o7planning.entity.PersonValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfig {
    @Bean(name = "validator")
    public PersonValidator validator(){
        return new PersonValidator();
    }

}
