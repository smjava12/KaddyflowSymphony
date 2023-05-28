/**
 * 
 */
package com.labs.kaddy.flow.system.service.level;

import java.io.Serializable;
import java.util.Map;

import com.labs.kaddy.flow.system.dto.request.LevelRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.LevelDetailsList;
import com.labs.kaddy.flow.system.service.base.BaseService;

/**
 * @author Surjyakanta
 *
 */
public interface LevelService<T, I extends Serializable> extends BaseService<T,I> {

	LevelDetailsList getLevelListById(Integer workflowId, Integer offset, Integer limit);

	BaseResponse saveLevel(LevelRequestVo levelRequestVo, Integer workflowId);

	BaseResponse update(Map<String, Object> changes, Integer id, String userId);

	BaseResponse deleteLevelRequest(Integer id, String userId);

}
