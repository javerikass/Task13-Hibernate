package ru.clevertec.house.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.clevertec.house.config.HibernateConfig;
import ru.clevertec.house.config.YamlPropertySourceFactory;
import ru.clevertec.house.dao.jdbc.ConnectionPool;

@EnableWebMvc
@Configuration
@Import(HibernateConfig.class)
@ComponentScan(basePackages = "ru.clevertec.house")
@PropertySource(value = "classpath:application.yml", factory = YamlPropertySourceFactory.class)
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public JdbcTemplate jdbcTemplate(ConnectionPool connectionPool) {
        return new JdbcTemplate(connectionPool.getDataSource());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}

