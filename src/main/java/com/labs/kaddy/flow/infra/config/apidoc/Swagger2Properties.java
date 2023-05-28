/**
 * 
 */
package com.labs.kaddy.flow.infra.config.apidoc;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Surjyakanta
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix= "application.swagger2", ignoreUnknownFields = false)
public class Swagger2Properties {
	private Boolean enabled;
	private String pathPattern;

	private String title;
	private String description;
	private String version;
	private String termOfServiceUrl;
	private String license;
	private String licenseUrl;
	private String contact;
	
}
