//package com.o7planning.config;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.*;
//
//import javax.sql.DataSource;
//
//@Configuration
//@PropertySources({@PropertySource("classpath:application.yml")})
//public class DataSourceConfig {
//
//    @Primary
//    @Bean("dataSource1")
//   // @ConfigurationProperties(prefix = "spring.app.datasource")
//    public DataSource appDataSource() {
//        return DataSourceBuilder
//                .create()
//                .build();
//    }
//
//    @Bean("dataSource2")
//  //  @ConfigurationProperties(prefix = "spring.security.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder
//                .create()
//                .build();
//    }
//}
