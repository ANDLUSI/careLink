package com.csi.config;

import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(DataSourceConfig.class)
@Slf4j
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource); // 设置数据源

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 启用下划线到驼峰命名的映射
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Slf4jImpl.class);
        // 设置自动映射行为为 FULL
        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        // 将配置设置到 SqlSessionFactoryBean
        sqlSessionFactoryBean.setConfiguration(configuration);

        // 设置类型别名包，方便在 XML 映射文件中使用
        sqlSessionFactoryBean.setTypeAliasesPackage("com.csi.domain");

        // 创建分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        // 设置数据库方言为 MySQL
        properties.setProperty("helperDialect", "mysql");
        // 将属性设置到插件
        pageInterceptor.setProperties(properties);
        // 将分页插件添加到 SqlSessionFactoryBean
        sqlSessionFactoryBean.setPlugins(pageInterceptor);

        // 返回配置好的 SqlSessionFactoryBean
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        // 设置扫描的 Mapper 接口包
        mapperScannerConfigurer.setBasePackage("com.csi.mapper");
        return mapperScannerConfigurer;
    }
}
