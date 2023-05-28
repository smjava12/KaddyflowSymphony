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
public class ErrorMessage implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 7149425830207480596L;
	
	@ApiModelProperty(value = "systemMessage" , allowEmptyValue = true)
	private List<SystemMessage> systemMessages;
}
