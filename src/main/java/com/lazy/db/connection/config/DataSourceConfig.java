package com.lazy.db.connection.config;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

@Configurable
@Configuration
public class DataSourceConfig {
	@Autowired
	private Environment env;

	private DataSource buildDataSource() {
		final HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setPoolName("hbm-pool");
		hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("useServerPrepStmts", "false");
		hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
		hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "false");
		hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		hikariConfig.setJdbcUrl(env.getProperty("spring.datasource.url"));
		hikariConfig.setUsername("postgres");
		hikariConfig.setPassword("Wellness36@");
		hikariConfig.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		hikariConfig.setAutoCommit(false);
		hikariConfig.setIsolateInternalQueries(true);
		hikariConfig.setMinimumIdle(5);
		hikariConfig.setMaximumPoolSize(250);
		hikariConfig.setIdleTimeout(60000);
		hikariConfig.setMaxLifetime(30000);
		hikariConfig.setConnectionTimeout(30000);
		hikariConfig.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED+"");
		hikariConfig.setLeakDetectionThreshold(2000000);

		return new HikariDataSource(hikariConfig);
	}

    @Bean
    DataSource dataSource() {
		ChainListener listener = new ChainListener();
		SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
		loggingListener.setQueryLogEntryCreator(new DefaultQueryLogEntryCreator());
		listener.addListener(loggingListener);
		listener.addListener(new DataSourceQueryCountListener());
		DataSource loggingDataSource = ProxyDataSourceBuilder.create(buildDataSource()).name("DATA_SOURCE_PROXY_NAME")
				.listener(listener).build();
		return new LazyConnectionDataSourceProxy(loggingDataSource);
	}

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPersistenceUnitName(getClass().getSimpleName());
		entityManagerFactoryBean.setPackagesToScan("com.lazy.db.connection");
		entityManagerFactoryBean.setDataSource(dataSource);
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties(properties());
		return entityManagerFactoryBean;
	}

	void poolingDataSource() {

	}

	Properties properties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		return properties;
	}
}
