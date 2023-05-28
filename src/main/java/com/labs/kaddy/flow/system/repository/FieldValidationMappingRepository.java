/**
 * 
 */
package com.labs.kaddy.flow.system.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.labs.kaddy.flow.system.entity.FieldValidationMapping;

/**
 * @author Surjyakanta
 *
 */
@Repository
public interface FieldValidationMappingRepository extends CrudRepository<FieldValidationMapping, Integer> {

}
