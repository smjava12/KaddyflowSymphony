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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Surjyakanta
 *
 */
@ApiModel(description="All details about the Labels")  
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(
		exclude = {"workflowRequest"}
	)
@Entity
@Table(
		name="LEVEL_REQUEST",
		schema = "KADDYFLOW"
	)
public class LevelRequest implements Serializable {

	private static final long serialVersionUID = 2230547029039830100L;

	@Id
	@SequenceGenerator(
			name = "levelrequest_sequence",
			sequenceName = "levelrequest_sequence",
			allocationSize = 1
		)
	@GeneratedValue(
			//strategy = GenerationType.IDENTITY
			strategy = GenerationType.SEQUENCE,
			generator = "levelrequest_sequence"
		)
	private Integer id;

	private String workflowId;
	
	@Column(
			name = "title",
			nullable = false
	)
	private String title;

	private String type;
	
	private boolean isActive;

	private boolean isValidation;

	private String category;

	@JsonIgnore
	@OneToMany(
			mappedBy="levelRequest",
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER, 
			orphanRemoval = true
		)
	private List<FieldRequest> fieldEntity;

	@ManyToOne(
			fetch = FetchType.EAGER,
			cascade = CascadeType.ALL
		)
	@JoinColumn(
			name = "workflowRequest_id"
			//insertable = false,
			//updatable = false
		)
	private WorkflowRequest workflowRequest;

	private String createdBy;
	
	private String updatedBy;
	
	@Column(name="createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm ss")
	@CreationTimestamp
	private Timestamp createdAt;
    
    @Column(name="updatedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm ss")
    @UpdateTimestamp
	private Timestamp updatedAt;
}
