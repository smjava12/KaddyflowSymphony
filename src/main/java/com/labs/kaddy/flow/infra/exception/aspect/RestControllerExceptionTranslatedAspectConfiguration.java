/**
 * 
 */
package com.labs.kaddy.flow.infra.exception.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * RestControllerExceptionTranslatedAspectConfiguration
 * 
 * @author Surjyakanta
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class RestControllerExceptionTranslatedAspectConfiguration {
	/**
	 * exceptionTranslatedAspect
	 * 
	 * @return RestControllerExceptionTranslatedAspect
	 */
	@Bean
	public RestControllerExceptionTranslatedAspect exceptionTranslatedAspect() {
		return new RestControllerExceptionTranslatedAspect();
	}
}
