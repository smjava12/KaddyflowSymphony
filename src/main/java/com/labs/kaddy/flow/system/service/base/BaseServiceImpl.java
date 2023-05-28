package com.labs.kaddy.flow.system.service.base;

import java.io.Serializable;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.labs.kaddy.flow.infra.exception.ApiAbortedException;
import com.labs.kaddy.flow.infra.exception.WorkflowNotFoundException;
import com.labs.kaddy.flow.system.dto.request.BaseRequest;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;
import com.labs.kaddy.flow.system.dto.response.CommonResponse;
import com.labs.kaddy.flow.system.enums.Action;
import com.labs.kaddy.flow.system.enums.Errors;

@Service
public abstract class BaseServiceImpl<T, I extends Serializable> implements BaseService<T, I> {
	
	private static final Logger LOGGER = ESAPI.getLogger(BaseServiceImpl.class);
	
	protected abstract CrudRepository<T, I> getRepository();

	protected abstract T convertObjectToEntity(BaseRequest request, Action action) throws Exception;

	@Override
	public BaseResponse insert(T t) {
		BaseResponse response = new CommonResponse();
		Object responseData = null;

		try {
			responseData = getRepository().save(t);
			response.setStatus("200", "Data is successfully added", responseData, null, null);
		} catch (Exception ex) {
			response.setStatus("400", null, responseData, ex.getMessage(), ex.getMessage());
			LOGGER.error(Logger.EVENT_FAILURE, "Workflow:{} is not saved due to exception: {}..." +ex );
			throw new ApiAbortedException(Errors.SVC_10_500_7, "Data persistent failure" , ex);
		}

		return response;
	}

	@Override
	public BaseResponse insert(BaseRequest request) {
		BaseResponse response = new CommonResponse();
		Object responseData = null;
		try {
			responseData = save(request, Action.INSERT);
			response.setStatus("200", "Data is successfully added", responseData, null, null);
		} catch (Exception ex) {
			response.setStatus("400", null, responseData, ex.getMessage(), ex.getMessage());
			LOGGER.error(Logger.EVENT_FAILURE, "Workflow:{} is not saved due to exception: {}..." +ex );
			throw new ApiAbortedException(Errors.SVC_10_500_7, "Data persistent failure" , ex);
		}
		return response;
	}

	private Object save(BaseRequest request, Action action) throws Exception {
		T baseEntity = null;
		baseEntity = convertObjectToEntity(request, action);

		try {
			return getRepository().save(baseEntity);
		} catch (Exception ex) {
			LOGGER.error(Logger.EVENT_FAILURE, "Workflow:{} is not saved due to exception: {}..." +ex );
			throw new ApiAbortedException(Errors.SVC_10_500_7, "Data persistent failure" , ex);
		}
	}

	@Override
	public BaseResponse update(BaseRequest request) {
		BaseResponse response = new CommonResponse();
		Object responseData = null;
		try {
			responseData = save(request, Action.UPDATE);
			response.setStatus("200", "Data is successfully updated", responseData, null, null);
		} catch (Exception ex) {
			response.setStatus("400", null, responseData, ex.getMessage(), ex.getMessage());
			LOGGER.error(Logger.EVENT_FAILURE, "Workflow:{} is not saved due to exception: {}..." +ex );
			throw new ApiAbortedException(Errors.SVC_10_500_7, "Data persistent failure" , ex);
		}
		return response;
	}

	@Override
	public BaseResponse delete(I id) {
		BaseResponse response = new CommonResponse();
		Object responseData = null;
		try {
			getRepository().deleteById(id);
			response.setStatus("200", "Data with Id " + id + " deleted successfully", responseData, null, null);
		} catch (Exception ex) {
			response.setStatus("400", null, responseData, ex.getMessage(), ex.getMessage());
			LOGGER.error(Logger.EVENT_FAILURE, "Failed to find and modify workflow:{}...");
			throw new ApiAbortedException(Errors.SVC_10_500_1, "" , ex);
		}
		return response;
	}

	@Override
	public BaseResponse exists(I id) {
		BaseResponse response = new CommonResponse();
		Object responseData = null;
		try {
			responseData = getRepository().existsById(id);
			response.setStatus("200", "Data is available", responseData, null, null);
		} catch (Exception ex) {
			response.setStatus("400", null, responseData, ex.getMessage(), ex.getMessage());
			LOGGER.error(Logger.EVENT_FAILURE, "Workflow:{} not present: {}...");
			throw new ApiAbortedException(Errors.SVC_10_500_1, "" , ex);
		}
		return response;
	}

	@Override
	public BaseResponse getById(I id) {
		BaseResponse response = new CommonResponse();
		Object responseData = null;

		try {
			if (id == null) {
				throw new Exception("Data not available when id is null or negative");
			}
			responseData = getRepository().findById(id).get();
			response.setStatus("200", "Search Successful", responseData, null, null);
		} catch (Exception e) {
			response.setStatus("400", null, responseData, e.getMessage(), e.getMessage());
			LOGGER.error(Logger.EVENT_FAILURE, "Workflow:{} is not present: {}...");
			throw new WorkflowNotFoundException(Errors.SVC_10_404_1, (String)id);
		}
		return response;
	}

	@Override
	public BaseResponse count(String searchRequest) {
		BaseResponse response = new CommonResponse();
		Object responseData = null;
		try {
			if (searchRequest == null) {
				responseData = getRepository().count();
			} else {
				responseData = getRepository().count();// getRepository().count(Example.of(getFilterCriteria(searchRequest)));
			}
			response.setStatus("200", "count is: ", responseData, null, null);
		} catch (Exception ex) {
			response.setStatus("400", null, responseData, ex.getMessage(), ex.getMessage());
			LOGGER.error(Logger.EVENT_FAILURE, "Workflow:{} is not present: {}...");
			throw new WorkflowNotFoundException(Errors.SVC_10_404_1, searchRequest);
		}
		return response;
	}

	@Override
	public BaseResponse getAll(Integer pageNumber, Integer numberOfRecords, String searchRequest, boolean pagination) {

		if (pageNumber == null || pageNumber < 1) {
			pageNumber = 0;
		}
		if (numberOfRecords == null || numberOfRecords < 10) {
			numberOfRecords = 10;
		}

		BaseResponse response = new CommonResponse();
		Object responseData = null;

		try {
			responseData = getAllData(pageNumber, numberOfRecords, searchRequest, pagination);// getRepository().findAll(Example.of(getFilterCriteria(searchRequest)));
			response.setStatus("200", "Search Successful", responseData, null, null);
		} catch (Exception e) {
			response.setStatus("400", null, responseData, e.getMessage(), e.getMessage());
			LOGGER.error(Logger.EVENT_FAILURE, "Workflow:{} is not present: {}...");
			throw new WorkflowNotFoundException(Errors.SVC_10_404_1, searchRequest);
		}
		return response;
	}

	private Object getAllData(Integer pageNumber, Integer numberOfRecords, String searchRequest, boolean pagination)
			throws Exception {
		if (pagination) {
			@SuppressWarnings("unused")
			Pageable pageable = PageRequest.of(pageNumber, numberOfRecords);
			//TODO find pagination solution
			return getRepository().findAll();
		} else {
			return getRepository().findAll();
		}
	}
	

}

