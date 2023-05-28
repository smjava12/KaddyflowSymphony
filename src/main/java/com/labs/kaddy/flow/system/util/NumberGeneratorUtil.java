/**
 * 
 */
package com.labs.kaddy.flow.system.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author Surjyakanta
 *
 */
public class NumberGeneratorUtil {

	public static String hexadecimalNumberGenerator() {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(0x1000000);
		String formatted = String.format("%06x", num); 
		System.out.println(formatted);
		return formatted;
	}
	
	public static String randomStringGenerator() {
	        String uuid = UUID.randomUUID().toString();
	        return uuid;
	}
}
