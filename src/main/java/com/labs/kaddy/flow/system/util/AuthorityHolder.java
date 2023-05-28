/**
 * 
 */
package com.labs.kaddy.flow.system.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Surjyakanta
 *
 */
@Getter
@AllArgsConstructor
public class AuthorityHolder {
	private String xCustomerId;
	private String xEmployeeId;
	private String xOperatorId;
	
	public boolean isOnlyOneIdIsNotEmpty() {
		return (!StringUtil.isBlank(xCustomerId) && StringUtil.isEmpty(xOperatorId) && StringUtil.isEmpty(xEmployeeId))
				|| (StringUtil.isEmpty(xCustomerId) && !StringUtil.isEmpty(xOperatorId) && StringUtil.isEmpty(xEmployeeId))
				|| (StringUtil.isEmpty(xCustomerId) && StringUtil.isEmpty(xOperatorId) && !StringUtil.isEmpty(xEmployeeId));
		
	} 
}
