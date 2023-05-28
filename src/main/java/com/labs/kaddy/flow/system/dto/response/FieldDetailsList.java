/**
 * 
 */
package com.labs.kaddy.flow.system.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Surjyakanta
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Field Response DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldDetailsList  implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1151721294799423607L;

	@ApiModelProperty(value = "FieldResponse" , allowEmptyValue = true, position = 1)
	private List<FieldResponse> fieldResponse;
	
	@ApiModelProperty(value = "count" , allowEmptyValue = true, position = 2)
	private int count;
	
	@ApiModelProperty(value = "Pagination" , allowEmptyValue = true, position = 3)
	private Pagination pagination;
	
}