/**
 * 
 */
package com.labs.kaddy.flow.system.service.base;

/**
 * @author mak
 *
 */
import java.io.Serializable;

import com.labs.kaddy.flow.system.dto.request.BaseRequest;
import com.labs.kaddy.flow.system.dto.response.BaseResponse;

public interface BaseService<T, I extends Serializable> {
	
	/**
	 * This method is responsible to handle select requests as well as search requests.
	 * @param pageNumber the page number default set to 0
	 * @param numberOfRecords the number of records to be returned default 10
	 * @param searchRequest the filter criteria
	 * @param pagination true if you need pagination false otherwise
	 * @return BaseResponse Object with data if it is there else Error Object
	 */
	
	public BaseResponse getAll(Integer pageNumber, Integer numberOfRecords, String searchRequest, boolean pagination);

	//-------------------------------------------------------------------------
	/**
	 * This method is responsible to count the number of objects in database with the specified criteria
	 * @param searchstr
	 * @return BaseResponse Object with data if it is there else Error Object
	 */
		
	public BaseResponse count(String searchstr);
	//-------------------------------------------------------------------------
	
	/**
	 * Method to delete an object
	 * @param itemId the item id which is to be deleted
	 * @return Base Response
	 */
	
	
	public BaseResponse delete(I itemId);
	//-------------------------------------------------------------------------
	/**
	 * Method to extract an object of type Entity using the Id. The Id is mandatory to be Integer.
	 * @param itemId the item id which needs to be extracted
	 * @return BaseResponse Object with the targeted object of type Entity; Error if id is null or less than 0
	 */
	
	public BaseResponse getById(I id);
	//-------------------------------------------------------------------------
	/**
	 * The method is to identify whether any data exists with certain parameters or not
	 * @param id the input id
	 * @return BaseResponse
	 */
	public BaseResponse exists(I id);
	//-------------------------------------------------------------------------
	
	
	/**
	 * Method to insert the object into the table
	 * @param request the object to be inserted
	 * @return BaseResponse with status
	 */
	public BaseResponse insert(BaseRequest request);
	//-------------------------------------------------------------------------
	
	/**
	 * Method to update the object into the table
	 * @param request the object to be inserted
	 * @return BaseResponse with status
	 */
	public BaseResponse update(BaseRequest request);

	
	/**
	 * Method to insert the object into the table
	 * @param request the object to be inserted
	 * @return BaseResponse with status
	 */
	
	public BaseResponse insert(T t);
	
	
}

