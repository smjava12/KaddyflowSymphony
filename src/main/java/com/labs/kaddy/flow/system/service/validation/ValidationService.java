/**
 * 
 */
package com.labs.kaddy.flow.system.service.validation;

import java.io.Serializable;
import java.util.Map;

import javax.validation.Valid;

import com.labs.kaddy.flow.system.dto.request.ValidationRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.ValidationDetailsList;
import com.labs.kaddy.flow.system.service.base.BaseService;

/**
 * @author Surjyakanta
 *
 */
public interface ValidationService<T, I extends Serializable> extends BaseService<T,I>  {

	BaseResponse saveValidation(ValidationRequestVo validationRequestVo, Integer fieldRequestId);

	ValidationDetailsList getFieldListById(Integer fieldRequestId, Integer offset, Integer limit);

	BaseResponse updateValidation(@Valid Map<String, Object> changes, Integer id, String string);

	BaseResponse deleteField(Integer id, String string);

}
