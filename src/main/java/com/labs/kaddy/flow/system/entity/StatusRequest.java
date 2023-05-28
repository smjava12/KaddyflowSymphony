/**
 * 
 */
package com.labs.kaddy.flow.system.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Surjyakanta
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StatusRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2025925865317003070L;

	@Id
	@SequenceGenerator(
				name = "statusrequest_sequence",
				sequenceName = "statusrequest_sequence",
				allocationSize = 1
			)
	@GeneratedValue(
				//strategy = GenerationType.IDENTITY
				strategy = GenerationType.SEQUENCE,
				generator = "statusrequest_sequence"
			)
	private Integer id;
	
	private String labelAliasName;
	
	/*
	 * @OneToOne
	 * 
	 * @JoinColumn( name = "workflowRequest_id", referencedColumnName = "id" )
	 */
	private WorkflowRequest workflowRequest;
	
	@Column(name="createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm ss")
	@CreationTimestamp
	private Timestamp createdAt;
    
    @Column(name="updatedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm ss")
    @UpdateTimestamp
	private Timestamp updatedAt; 
	
	
}
