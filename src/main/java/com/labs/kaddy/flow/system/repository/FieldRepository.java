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

import com.labs.kaddy.flow.system.entity.FieldRequest;

/**
 * @author Surjyakanta
 *
 */
@Repository
public interface FieldRepository extends CrudRepository<FieldRequest, Integer> {

	Optional<List<FieldRequest>> findByLevelRequestIdAndIsActiveTrue(@Param(value = "levelRequestId") Integer levelRequestId);
	
	Long countByLevelRequestId(@Param(value = "levelRequestId") Integer levelRequestId);

	int countByIdAndIsActiveTrue(Integer id);

	@Modifying
	@Query(value = "UPDATE FIELD_REQUEST SET IS_ACTIVE =:b , UPDATED_BY=:userId "
			+ "WHERE ID=:id", nativeQuery = true)
	int deleteField(boolean b, Integer id, String userId);

	Optional<FieldRequest> findByIdAndIsActiveTrue(Integer id);
}
