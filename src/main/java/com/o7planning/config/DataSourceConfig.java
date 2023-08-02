package com.o7planning.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import javax.sql.DataSource;

@Configuration
@PropertySources({@PropertySource("classpath:application.yml")})
public class DataSourceConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.app.datasource1")
    public DataSource appDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.app.datasource1.dataSourceClassName"));
        dataSource.setUrl(env.getProperty("spring.app.datasource1.url"));
        dataSource.setUsername(env.getProperty("spring.app.datasource1.username"));
        dataSource.setPassword(env.getProperty("spring.app.datasource1.password"));

        System.out.println("## DataSource2: " + dataSource);

        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.security.datasource2")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }
}
