/**
 * 
 */
package com.labs.kaddy.flow.system.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.labs.kaddy.flow.system.entity.WorkflowRequest;

/**
 * @author mak
 *
 */

@Repository
public interface WorkflowRepository extends JpaRepository<WorkflowRequest, Integer> {

	List<WorkflowRequest> findByIdAndIsActiveTrue(@Param(value = "workflowId") Integer workflowId);

	Long countByIdAndIsActiveTrue(@Param(value = "workflowId") Integer workflowId);

	List<Map<String, Long>> countByAuthorAndIsActiveTrue(@Param(value = "author") String author);

	Optional<List<WorkflowRequest>> findAllWorkFlowByAuthorAndIsActiveTrue(@Param(value = "author") String author);

	@Modifying
	@Query(value = "UPDATE WORKFLOW_REQUEST SET TITLE=:title , UPDATED_BY=:userId "
			+ "WHERE ID=:workflowId AND is_active = 1", nativeQuery = true)
	int updateWorkflow(String title, Integer workflowId, String userId);

	@Modifying
	@Query(value = "UPDATE WORKFLOW_REQUEST SET IS_ACTIVE =:b , UPDATED_BY=:userId "
			+ "WHERE ID=:workflowId", nativeQuery = true)
	int deleteWorkflow(boolean b, Integer workflowId, String userId);
}
