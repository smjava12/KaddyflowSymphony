/**
 * 
 */
package com.labs.kaddy.flow.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.labs.kaddy.flow.system.entity.ValidationRequest;

/**
 * @author Surjyakanta
 *
 */
@Repository
public interface ValidationRepository extends CrudRepository<ValidationRequest, Integer>  {

	Optional<List<ValidationRequest>> findByFieldRequestIdAndIsActiveTrue(@Param(value = "fieldRequestId") Integer fieldRequestId);

	Long countByFieldRequestId(@Param(value = "fieldRequestId") Integer fieldRequestId);
	
	int countByIdAndIsActiveTrue(Integer id);

	@Modifying
	@Query(value = "UPDATE VALIDATION_REQUEST SET IS_ACTIVE =:b , UPDATED_BY=:userId "
			+ "WHERE ID=:id", nativeQuery = true)
	int deleteField(boolean b, Integer id, String userId);

	Optional<ValidationRequest> findByIdAndIsActiveTrue(Integer id);
}
