/**
 * 
 */
package com.labs.kaddy.flow.system.dto.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Surjyakanta
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Pagination")
public class Pagination implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -1598630276919227917L;
	
	@ApiModelProperty(value= "offset", allowEmptyValue = true)
	private Integer offset;
	
	@ApiModelProperty(value= "limit", allowEmptyValue = true)
	private Integer limit;
	
	@ApiModelProperty(value= "totalRecords", allowEmptyValue = true)
	private Integer totalRecords;
}
