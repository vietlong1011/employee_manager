package com.o7planning.config.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

// config to User Security
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.o7planning.repository.URepository",
        entityManagerFactoryRef = "db2EntityManagerFactory",
        transactionManagerRef = "db2TransactionManager")
public class DB2Config {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.db2")
    public DataSourceProperties db2DataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource db2DataSource(){
        return  db2DataSourceProperties().initializeDataSourceBuilder().type(DriverManagerDataSource.class).build();
    }


    @Bean(name = "db2EntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder){
        return  builder.dataSource(db2DataSource()).packages("com.o7planning.entity.user").build();
    }


    @Bean(name = "db2TransactionManager")
    @Primary
    public PlatformTransactionManager db2TransactionManager(
            final  @Qualifier("db2EntityManagerFactory") LocalContainerEntityManagerFactoryBean db1EntityManagerFactory){
        return new JpaTransactionManager(db1EntityManagerFactory.getObject());
    }

}
