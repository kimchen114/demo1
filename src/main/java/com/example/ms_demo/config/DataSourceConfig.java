package com.example.ms_demo.config;

import org.apache.tomcat.jdbc.pool.DataSource;

// import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DataSourceConfig {
    
    @Autowired
    private Environment env;
    
    @Bean
    public javax.sql.DataSource dataSource() {
        DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource();
        PoolProperties properties = new PoolProperties();
        properties.setUrl(env.getProperty("spring.datasource.url"));
        properties.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        properties.setUsername(env.getProperty("spring.datasource.username"));
        properties.setPassword(env.getProperty("spring.datasource.password"));
        datasource.setPoolProperties(properties);
//        datasource.s
        return datasource;
    }
    
    // @Bean
    // public SqlSessionFactoryBean sqlSessionFactoryBean(){
    // SqlSessionFactoryBean factory=new SqlSessionFactoryBean();
    // factory.setDataSource(dataSource());
    // factory.setTypeAliasesPackage("com.example.ms_demo");
    // org.apache.ibatis.session.Configuration configuration=new
    // org.apache.ibatis.session.Configuration() ;
    // factory.setConfiguration(configuration);
    // factory.setVfs(SpringBootVFS.class);
    // return factory;
    //
    // }
    
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
}