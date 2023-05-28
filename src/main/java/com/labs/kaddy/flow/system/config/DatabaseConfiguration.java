/**
 * 
 */
package com.labs.kaddy.flow.system.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.labs.kaddy.flow.system.enums.Errors;
import com.labs.kaddy.flow.infra.exception.ApiAbortedException;
import com.zaxxer.hikari.HikariDataSource;

import lombok.AllArgsConstructor;

/**
 * @author Surjyakanta
 *
 */
@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
@AllArgsConstructor
public class DatabaseConfiguration {

	private final DatabaseProperties properties;
	
	private static final String DB_UNAME= "username";
	private static final String DB_PASS= "password";
	private static final String DB_URL= "url";
	
	@Primary
	@Bean
	@ConfigurationProperties(prefix= "spring.datasource.hikari")
	public DataSource dataSource() {
		Properties prop = new Properties();
		try(InputStream inputStream = new FileInputStream(properties.getDbFilePath())) {
			prop.load(inputStream);
		} catch (IOException ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "Missing properties file location : "
					+ properties.getDbFilePath() , ex);
		}
		return DataSourceBuilder.create()
				.type(HikariDataSource.class)
				.driverClassName(properties.getDriverClassName())
				.url(prop.getProperty(DB_URL))
				.username(prop.getProperty(DB_UNAME))
				.password(prop.getProperty(DB_PASS))
				.build();
	}
	
}
