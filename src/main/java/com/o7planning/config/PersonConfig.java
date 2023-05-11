package com.o7planning.config;

import com.o7planning.entity.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class PersonConfig {
    @Bean(name = "validator")
    public PersonValidator validator() {
        return new PersonValidator();
    }

    // config convert entity to dto but not used
    @Bean(name = "moderMapper")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
