/**
 * 
 */
package com.labs.kaddy.flow.infra.config.logging.access;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * RestControllerAccessLoggingAspectConfiguration
 * 
 * @author Surjyakanta
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class RestControllerAccessLoggingAspectConfiguration {

	/**
	 * accessLoggingAspect
	 * 
	 * @return RestControllerAccessLoggingAspect
	 */
	@Bean
	public  RestControllerAccessLoggingAspect accessLoggingAspect() {
		return new RestControllerAccessLoggingAspect();
	}
}
