/**
 * 
 */
package com.labs.kaddy.flow.system.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Surjyakanta
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldValidationMappingRequestVo implements BaseRequest  {
	
	private Integer id;

	private Integer validationID;
	
	private Integer fieldID;

	private String title;

	private String toolTip;

	private boolean isActive;

	@Override
	public Integer getIdentifier() throws Exception {
		return id;
	}
}
