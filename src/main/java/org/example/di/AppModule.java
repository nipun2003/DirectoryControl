package org.example.di;

import org.example.data.repository.FileDataRepositoryImpl;
import org.example.data.repository.MainRepositoryImpl;
import org.example.domain.repository.FileDataRepository;
import org.example.domain.repository.MainRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppModule {

    private DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/maya");
        dataSource.setUsername("lucifer");
        dataSource.setPassword("2003");
        return dataSource;
    }

    private JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    private FileDataRepository getFileDataRepository() {
        return new FileDataRepositoryImpl(getJdbcTemplate());
    }

    @Bean
    public MainRepository getMainRepository() {
        return new MainRepositoryImpl(getFileDataRepository());
    }
}
