/**
 * 
 */
package com.labs.kaddy.flow.infra.config.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author Surjyakanta
 *
 */
@Configuration
public class MessageConfiguration {

	private static final String ERROR_MESSAGE_BASENAME = "message.error";
	
	/**
	 * messageSource
	 * 
	 * @param messageSource type of MessageSource
	 * 
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(ERROR_MESSAGE_BASENAME);
		return messageSource;
	}
}
