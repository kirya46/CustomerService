package com.common.config;

import com.common.domain.Customer;
import com.common.persistence.CustomerMapper;
import org.hibernate.SessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Kirill Stoianov on 15/09/17.
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)

public class DataConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public org.h2.tools.Server h2WebConsonleServer() throws SQLException {
        return org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "8082");
    }

    @Bean
    public CustomerMapper customerMapper() {
        return new CustomerMapper();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQL95Dialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.lazy", "false");
        return properties;
    }

    @Bean
    public HibernateTemplate hibernateTemplate() {
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory());
        hibernateTemplate.setCheckWriteOperations(false);
        return hibernateTemplate;
    }

    @Bean
    public SessionFactory sessionFactory() {

        //set model package
        final String modelPackage = Customer.class.getPackage().getName();

        final LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(getDataSource());

        return localSessionFactoryBuilder
                .scanPackages(modelPackage)
                .addProperties(hibernateProperties())
                .buildSessionFactory();
    }

    @Bean
    public DataSource getDataSource() {
//        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//        dataSource.setDriverClass(org.h2.Driver.class);
//        dataSource.setUsername("admin");
//        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//        dataSource.setPassword("");
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername("kirya");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/customerdb");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
