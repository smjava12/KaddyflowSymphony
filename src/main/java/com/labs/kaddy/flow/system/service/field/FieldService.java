/**
 * 
 */
package com.labs.kaddy.flow.system.service.field;

import java.io.Serializable;
import java.util.Map;

import javax.validation.Valid;

import com.labs.kaddy.flow.system.dto.request.FieldRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.FieldDetailsList;
import com.labs.kaddy.flow.system.service.base.BaseService;

/**
 * @author Surjyakanta
 *
 */
public interface FieldService<T, I extends Serializable> extends BaseService<T,I> {

	BaseResponse saveField(FieldRequestVo fieldRequestVo, Integer levelRequestId);

	FieldDetailsList getFieldListById(Integer levelRequestId, Integer offset, Integer limit);

	BaseResponse updateField(@Valid Map<String, Object> changes, Integer id, String string);

	BaseResponse deleteField(Integer id, String string);

}
