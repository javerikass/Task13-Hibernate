package ru.clevertec.ecl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.clevertec.ecl.dao.jdbc.ConnectionPool;

@Configuration
@ComponentScan(basePackages = "ru.clevertec.ecl")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
@Import(HibernateConfig.class)
public class AppConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(ConnectionPool connectionPool) {
        return new JdbcTemplate(connectionPool.getDataSource());
    }

}
