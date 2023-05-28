/**
 * 
 */
package com.labs.kaddy.flow.infra.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.labs.kaddy.flow.system.enums.Errors;

import lombok.AllArgsConstructor;

/**
 * This is ErrorMessageService class to display error message
 * @author Surjyakanta
 *
 */
@Service
@AllArgsConstructor
public class ErrorMessageService {

	private final MessageSource messageSource;
	
	/**
	 * @param key Errors
	 * @return String Message
	 */
	public String get(Errors key) {
		return messageSource.getMessage(key.name(), null, Locale.ENGLISH);
	}
	
	/**
	 * @param key Errors
	 * @param fieldName String
	 * @return String Message
	 */
	public String createMessage(Errors key, String fieldName) {
		return messageSource.getMessage(key.name(), null, Locale.ENGLISH).replace("%1", fieldName);
	}
}
