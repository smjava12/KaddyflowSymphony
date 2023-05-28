/**
 * 
 */
package com.labs.kaddy.flow.system.util;

import java.util.Map;

import javax.validation.Valid;

import com.labs.kaddy.flow.infra.exception.BadRequestParameterException;
import com.labs.kaddy.flow.system.enums.Errors;

/**
 * @author Surjyakanta
 *
 */
public class HeaderValidation {

	public static void validateHeader(AuthorityHolder headerObject, String accessLevelCode) {
		if(!headerObject.isOnlyOneIdIsNotEmpty()) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "X-Customer-ID, X-Employee-ID, X-Operator-ID");
		} else if (hasOperatorIdAndAccessLevelCodeIsNull(headerObject, accessLevelCode)) {
			throw new BadRequestParameterException(Errors.SVC_10_400_1, accessLevelCode);
		} else if (hasOperatorIdAndAccessLevelCodeNot1Or2Or3(headerObject, accessLevelCode)) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, accessLevelCode);
		}
	}
	
	private static boolean hasOperatorIdAndAccessLevelCodeNot1Or2Or3(AuthorityHolder headerObject, String accessLevelCode) {
		return !StringUtil.isEmpty(headerObject.getXOperatorId())
				&& (!"1".equals(accessLevelCode) && !"2".equals(accessLevelCode) && !"3".equals(accessLevelCode));
	}
	
	private static boolean hasOperatorIdAndAccessLevelCodeIsNull(AuthorityHolder headerObject, String accessLevelCode) {
		return !StringUtil.isEmpty(headerObject.getXOperatorId()) && (StringUtil.isEmpty(accessLevelCode));
	}

	public static void validateMap(@Valid Map<String, Object> changes) {
		
		  if (changes.isEmpty()) { 
			  throw new BadRequestParameterException(Errors.SVC_10_400_1,"change value missing"); 
		  }
	}
	
	public static void validateId(Integer id) {
		/*
		 * if (StringUtil.isBlank(id)) { throw new
		 * BadRequestParameterException(Errors.SVC_10_400_1,"ID"); }
		 */
	}
	
}
