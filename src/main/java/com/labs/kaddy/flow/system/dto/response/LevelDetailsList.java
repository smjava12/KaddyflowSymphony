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
@ApiModel(description = "Level Response DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LevelDetailsList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5165704369157728792L;

	@ApiModelProperty(value = "LevelResponse" , allowEmptyValue = true, position = 1)
	private List<LevelResponse> levelResponse;
	
	@ApiModelProperty(value = "count" , allowEmptyValue = true, position = 2)
	private int count;
	
	@ApiModelProperty(value = "Pagination" , allowEmptyValue = true, position = 3)
	private Pagination pagination;
	
}
