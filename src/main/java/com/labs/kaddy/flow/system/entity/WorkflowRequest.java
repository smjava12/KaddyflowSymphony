/**
 * 
 */
package com.labs.kaddy.flow.system.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mak
 * @param <Labels>
 *
 */
@ApiModel(description="All details about the Labels")  
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
		name="WORKFLOW_REQUEST",
		schema = "KADDYFLOW"
	)
public class WorkflowRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8270532226279216892L;

	@Id
	@SequenceGenerator(
			name = "workflowrequest_sequence",
			sequenceName = "workflowrequest_sequence",
			allocationSize = 1
		)
	@GeneratedValue(
			//strategy = GenerationType.IDENTITY
			strategy = GenerationType.SEQUENCE,
			generator = "workflowrequest_sequence"
		)
	private Integer id;
	
	//TODO later to be modified by UserEntity service oneToOne
	/*
	 * // @JsonIgnore
	 * 
	 * @ManyToOne(optional = true,fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name="userId", updatable=false, insertable=false, nullable =
	 * true) private Users user;
	 * 
	 * @column()
	 */
	private Integer userId;
	
	//add coulmn name in top for every column entity
	@Column(
			name = "title",
			nullable = false
	)
	private String title;
	
	private String author;
	
	private boolean isActive;
	
	private String workflowId;
	
	@JsonIgnore
	@OneToMany(
			mappedBy="workflowRequest",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY, 
			orphanRemoval = true
		)
	private List<LevelRequest> level;

	@Column(name="status")
	private Integer statusId;
	
	private String createdBy;
	
	private String updatedBy;
	
	@Column(name="createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm ss")
	@CreationTimestamp
	private Timestamp createdDateTimeStamp;
    
    @Column(name="updatedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm ss")
    @UpdateTimestamp
	private Timestamp updatedDateTimeStamp;
}