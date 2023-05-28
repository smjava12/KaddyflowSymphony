/**
 * 
 */
package com.labs.kaddy.flow.infra.constants;

/**
 * @author mak
 *
 */
public interface Constants {
	public static final String STATUS_CODE = "statusCode";
	public static final String STATUS_MESSAGE = "statusMessage";
	public static final String DATA = "data";
	public static final String ERROR_CODE = "errorCode";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String ERROR_FOUND = "Error found"; 
	public static final String ERROR_NOT_FOUND = "No error found"; 
	public static final String OPTIONAL = ""; 
	public static final String OPTIONAL_VALUE = ""; 
	
	public static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
	public static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	public static final String NUMBER = "0123456789";
	public static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
	
	public static final int ZERO = 0;
	
	public static final int MAX_LIMIT = 100;
	public static final int MIN_LIMIT = 1;
	public static final int OFFSET_MIN = 0;
}
