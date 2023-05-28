/**
 * 
 */
package com.labs.kaddy.flow.system.service.workflow;

import java.io.Serializable;

import com.labs.kaddy.flow.system.dto.request.WorkflowRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.WorkflowDetailsList;
import com.labs.kaddy.flow.system.service.base.BaseService;


/**
 * @author mak
 *
 */


public interface WorkflowService<T, I extends Serializable> extends BaseService<T,I> {

	WorkflowDetailsList getWorkflowListById(Integer workflowId, Integer offset, Integer limit);

	WorkflowDetailsList getList(String Author, Integer offset, Integer limit);

	int deleteWorkflow(Integer workflowId, String userId);

	int update(String title, Integer workflowId, String userId);

	BaseResponse saveWorkflow(WorkflowRequestVo workflowRequestVo);

}
