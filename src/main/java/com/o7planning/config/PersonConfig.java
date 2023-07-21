package com.o7planning.config;

import com.o7planning.entity.PersonValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PersonConfig {
    @Bean(name = "validator")
    public PersonValidator validator() {
        return new PersonValidator();
    }

    // config convert entity to dto but not used
    @Bean(name = "moderMapper")
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE); // mapper giua 2 value gan dung(MatchingStrategies.LOOSE)
        return modelMapper;
    }


}
