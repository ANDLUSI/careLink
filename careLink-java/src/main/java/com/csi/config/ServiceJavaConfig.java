package com.csi.config;

import jakarta.ejb.TransactionManagement;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
@TransactionManagement
//@Import(DataSourceConfig.class)
@Import({DataSourceConfig.class, MyBatisConfig.class, RedisJavaConfig.class})
@ComponentScan(value={"com.csi.service","com.csi.mapper"})
public class ServiceJavaConfig {

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
