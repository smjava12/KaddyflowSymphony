/**
 * 
 */
package com.labs.kaddy.flow.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.labs.kaddy.flow.system.entity.StatusRequest;

/**
 * @author Surjyakanta
 *
 */
@Repository
public interface StatusRepository extends CrudRepository<StatusRequest, Integer>  {

}
