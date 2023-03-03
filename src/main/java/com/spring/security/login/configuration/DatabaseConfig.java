package com.spring.security.login.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class DatabaseConfig {


    @Bean
    public ResourceDatabasePopulator resourceDatabasePopulator(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));
        populator.execute(dataSource);
        return populator;
    }
}
