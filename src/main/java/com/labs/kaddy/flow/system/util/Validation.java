/**
 * 
 */
package com.labs.kaddy.flow.system.util;

import com.labs.kaddy.flow.infra.constants.Constants;
import com.labs.kaddy.flow.infra.exception.BadRequestParameterException;
import com.labs.kaddy.flow.system.enums.Errors;

/**
 * This class is for validate header information
 * 
 * @author Surjyakanta
 *
 */
public class Validation {

	/**
	 * validate limitations
	 * 
	 * @param offset Integer
	 * @param limit Integer
	 * @return Nothing
	 * @throws BadRequestParameterException Exception
	 *
	 */
	public static void limitations(Integer offset, Integer limit) {
		if(offset < Constants.OFFSET_MIN) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, "offset");
		}
		if(limit < Constants.MIN_LIMIT || limit> Constants.MAX_LIMIT) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, "limit");
		}
	}
}
