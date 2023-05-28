/**
 * 
 */
package com.labs.kaddy.flow.system.dto.response;

import java.io.Serializable;
import java.util.List;

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
@ApiModel(description = "SystemMessage")
public class SystemMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7641711951865554169L;

	@ApiModelProperty(value = "messageCode" , allowEmptyValue = true)
	private String messageCode;
	
	@ApiModelProperty(value = "messageDescription" , allowEmptyValue = true)
	private String messageDescription;
}
