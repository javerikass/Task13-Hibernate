package ru.clevertec.house.config;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.clevertec.house.dao.jdbc.ConnectionPool;

@Configuration
@ComponentScan("ru.clevertec.house")
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public Properties hibernateProperties(@Value("classpath:application.yml") Resource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        return factory.getObject();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(
        @Qualifier("hibernateProperties") Properties hibernateProperties,
        ConnectionPool connectionPool) {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(connectionPool.getDataSource());
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        localSessionFactoryBean.setPackagesToScan("ru.clevertec.house.entity");
        return localSessionFactoryBean;
    }

    @Bean
    public TransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

}