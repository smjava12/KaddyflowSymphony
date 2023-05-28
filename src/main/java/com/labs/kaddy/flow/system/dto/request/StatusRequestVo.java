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
public class StatusRequestVo implements BaseRequest {
	
	private Integer id;
	
	private String labelAliasName;
	
	@Override
	public Integer getIdentifier() throws Exception {
		return id;
	}

}
