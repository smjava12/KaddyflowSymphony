/**
 * 
 */
package com.labs.kaddy.flow.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author Surjyakanta
 *
 */
@Data
@ConfigurationProperties(prefix = "application.database", ignoreUnknownFields = false)
public class DatabaseProperties {

	private String driverClassName;
	private String dbFilePath;
}
