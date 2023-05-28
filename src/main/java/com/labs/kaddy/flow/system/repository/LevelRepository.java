/**
 * 
 */
package com.labs.kaddy.flow.system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.labs.kaddy.flow.system.entity.LevelRequest;

/**
 * @author Surjyakanta
 *
 */

@Repository
public interface LevelRepository extends CrudRepository<LevelRequest, Integer> , QuerydslPredicateExecutor<LevelRequest>  {

	List<LevelRequest> findByWorkflowRequestIdAndIsActiveTrue(@Param(value = "workflowRequestId") Integer workflowRequestId);

	Long countByWorkflowRequestId(@Param(value = "workflowRequestId") Integer workflowRequestId);

	Optional<LevelRequest> findByIdAndIsActiveTrue(@Param(value = "id") Integer id);

	int countByIdAndIsActiveTrue(Integer id);

	@Modifying
	@Query(value = "UPDATE LEVEL_REQUEST SET IS_ACTIVE =:b , UPDATED_BY=:userId "
			+ "WHERE ID=:id", nativeQuery = true)
	int deleteLevel(boolean b, Integer id, String userId);
	
	//Optional<LevelRequest> updateLevelRequest(@Param(value = "id")Integer id,@Param(value = "levelRequestVo") LevelRequestVo levelRequestVo);

}
