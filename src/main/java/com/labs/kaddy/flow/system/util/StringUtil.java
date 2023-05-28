/**
 * 
 */
package com.labs.kaddy.flow.system.util;

import java.security.SecureRandom;

import com.labs.kaddy.flow.infra.constants.Constants;

/**
 * @author Surjyakanta
 *
 */
public class StringUtil {
	
	public static boolean isBlank(String value) {
		return (value == null || "null".equalsIgnoreCase(value) || value.isEmpty() || value.trim().equals(""));
	}
	
	public static boolean isEmpty(String value) {
		return (value == null || value.length() == 0 || "null".equalsIgnoreCase(value));
	}
	
	public static String generateRandomString() {
		int lenght = 10;
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(lenght);
		for(int i =0; i < lenght ; i++) {
			int rndCharAt = random.nextInt(Constants.DATA_FOR_RANDOM_STRING.length());
			char rndChar = Constants.DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
			sb.append(rndChar);
		}
		return sb.toString();
	}
}
//