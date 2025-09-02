package com.csi.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 配置DataSource
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
public class DataSourceConfig {

    @Value("${jdbc.url}")
    private String url ;

    @Value("${jdbc.classname}")
    private String className ;

    @Value("${jdbc.user}")
    private String username ;

    @Value("${jdbc.password}")
    private String password ;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource() ;
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(className);
        return dataSource ;
    }

}
