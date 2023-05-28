/**
 * 
 */
package com.labs.kaddy.flow.system.service.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labs.kaddy.flow.infra.exception.ApiAbortedException;
import com.labs.kaddy.flow.infra.exception.BadRequestParameterException;
import com.labs.kaddy.flow.system.dto.request.BaseRequest;
import com.labs.kaddy.flow.system.dto.request.WorkflowRequestVo;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.Pagination;
import com.labs.kaddy.flow.system.dto.response.WorkflowDetailsList;
import com.labs.kaddy.flow.system.dto.response.WorkflowResponse;
import com.labs.kaddy.flow.system.entity.WorkflowRequest;
import com.labs.kaddy.flow.system.enums.Action;
import com.labs.kaddy.flow.system.enums.Errors;
import com.labs.kaddy.flow.system.repository.WorkflowRepository;
import com.labs.kaddy.flow.system.service.base.BaseServiceImpl;
import com.labs.kaddy.flow.system.util.NumberGeneratorUtil;

/**
 * @author mak
 *
 */
@Service
public class WorkflowServiceImpl<T, I extends Serializable> extends BaseServiceImpl<T, I>
		implements WorkflowService<T, I> {

	@Autowired
	private WorkflowRepository workflowRepository;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected CrudRepository getRepository() {
		return workflowRepository;
	}

	Function<WorkflowRequestVo, WorkflowRequest> convert = new Function<WorkflowRequestVo, WorkflowRequest>() {

		@Override
		public WorkflowRequest apply(WorkflowRequestVo input) {
			WorkflowRequest entity = new WorkflowRequest();
			entity.setUserId(input.getUserId());
			entity.setTitle(input.getTitle());
			entity.setAuthor(input.getAuthor());
			entity.setActive(input.isActive());
			entity.setWorkflowId(NumberGeneratorUtil.randomStringGenerator());
			entity.setCreatedBy(input.getCreatedBy());
			entity.setUpdatedBy(input.getUpdatedBy());
			return entity;
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected T convertObjectToEntity(BaseRequest request, Action action) throws Exception {
		WorkflowRequest entity = null;
		switch (action) {
		case INSERT:
			entity = convert.apply((WorkflowRequestVo) request);
			break;
		case UPDATE:
			entity = convert.apply((WorkflowRequestVo) request);
			break;
		case SEARCH:
			entity = convert.apply((WorkflowRequestVo) request);
			// entity.setId(request.getIdentifier());
			break;
		}
		return (T) entity;
	}

	@Cacheable(cacheNames = "workflowDetailsList", key = "#workFlowId")
	@Override
	public WorkflowDetailsList getWorkflowListById(Integer workFlowId, Integer offset, Integer limit) {
		Long totalRecords = workflowRepository.countByIdAndIsActiveTrue(workFlowId);
		Integer totalCount = totalRecords.intValue();
		if (totalCount == 0) {
			return getEmptyWorkflowList();
		}
		if (offset >= totalCount) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, "offset");
		}
		List<WorkflowRequest> result = new ArrayList<>();
		try {
			result = workflowRepository.findByIdAndIsActiveTrue(workFlowId);
			// .stream().skip(offset).limit(limit).collect(Collectors.toList());
			Pagination pagiantion = new Pagination(offset, limit, totalCount);
			return getWorkflowList(result, pagiantion);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	private WorkflowDetailsList getEmptyWorkflowList() {
		WorkflowDetailsList workflowDetailsList = new WorkflowDetailsList();
		workflowDetailsList.setCount(0);
		return workflowDetailsList;
	}

	private WorkflowDetailsList getWorkflowList(List<WorkflowRequest> result, Pagination pagiantion) {
		WorkflowDetailsList wdList = new WorkflowDetailsList();
		try {
			wdList.setCount(result.size());
			List<WorkflowResponse> workdflowList = new ArrayList<>();
			for (WorkflowRequest req : result) {
				WorkflowResponse details = new WorkflowResponse();
				details.setUserId(req.getUserId());
				details.setActive(req.isActive());
				details.setAuthor(req.getAuthor());
				//details.setLebel(req.getLevel());
				//details.setStatus(req.getStatus());
				details.setStatusId(req.getStatusId());
				details.setTitle(req.getTitle());
				details.setWorkflowId(req.getWorkflowId());
				details.setCreatedBy(req.getCreatedBy());
				details.setUpdatedBy(req.getUpdatedBy());
				details.setUpdatedDateTimeStamp(req.getUpdatedDateTimeStamp());
				workdflowList.add(details);
			}
			wdList.setWorkflowResponse(workdflowList);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1,"",ex);
		}
		return wdList;
	}

	@Cacheable(cacheNames = "workflowDetailsList", key = "#author")
	@Override
	public WorkflowDetailsList getList(String author, Integer offset, Integer limit) {
		List<WorkflowRequest> result = null;
		long totalRecords = Long.parseLong(String.valueOf(workflowRepository.countByAuthorAndIsActiveTrue(author).get(0)));
		Integer totalCount = (int) totalRecords;
		if (totalCount == 0) {
			return getEmptyWorkflowList();
		}
		if (offset >= totalCount) {
			throw new BadRequestParameterException(Errors.SVC_10_400_3, "offset");
		}
		try {
			result = workflowRepository.findAllWorkFlowByAuthorAndIsActiveTrue(author).orElse(new ArrayList<>()).stream()
					.skip(offset).limit(limit).collect(Collectors.toList());
			Pagination pagiantion = new Pagination(offset, limit, totalCount);
			return getWorkflowList(result, pagiantion);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@Override
	@Transactional
	public int deleteWorkflow(Integer workflowId, String userId) {
		if(workflowRepository.countByIdAndIsActiveTrue(workflowId)<1) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "workflowId not found for: {} "+workflowId );
		}
		try {
			
			WorkflowRequest entity = new WorkflowRequest();
			entity.setUpdatedBy(userId);
			return workflowRepository.deleteWorkflow(false, workflowId, userId);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@Override
	@Transactional
	public int update(String title, Integer workflowId, String userId) {
		if(workflowRepository.countByIdAndIsActiveTrue(workflowId)<1) {
			throw new BadRequestParameterException(Errors.SVC_10_404_1, "workflowId not found for: {} "+workflowId );
		}
		
		try {
			return workflowRepository.updateWorkflow(title, workflowId, userId);
		} catch (Exception ex) {
			throw new ApiAbortedException(Errors.SVC_10_500_1, "", ex);
		}
	}

	@Override
	public BaseResponse saveWorkflow(WorkflowRequestVo workflowRequestVo) {
		BaseResponse response = insert(workflowRequestVo);
		
		WorkflowResponse WorkflowResponse = convertEntityToDtoResponse.apply((WorkflowRequest) response.getOutput().getData());
		
		response.setStatus("200", "Data is successfully added", WorkflowResponse, null, null);
		
		return response;
	}
	
	Function<WorkflowRequest, WorkflowResponse> convertEntityToDtoResponse = new Function<WorkflowRequest, WorkflowResponse>() {

		@Override
		public WorkflowResponse apply(WorkflowRequest input) {
			WorkflowResponse entity = new WorkflowResponse();
			entity.setId(input.getId());
			entity.setUserId(input.getUserId());
			entity.setTitle(input.getTitle());
			entity.setAuthor(input.getAuthor());
			entity.setActive(input.isActive());
			entity.setWorkflowId(input.getWorkflowId());
			return entity;
		}
	};
}
