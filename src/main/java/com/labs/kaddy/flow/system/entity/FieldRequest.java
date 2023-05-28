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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Surjyakanta
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(
		exclude = {"levelRequest"}
	)
@Table(
		name="FIELD_REQUEST",
		schema = "KADDYFLOW"
	)
@Entity
public class FieldRequest implements Serializable{

	private static final long serialVersionUID = 1788109896053600992L;
	
	@Id
	@SequenceGenerator(
			name = "fieldrequest_sequence",
			sequenceName = "fieldrequest_sequence",
			allocationSize = 1
		)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "fieldrequest_sequence"
		)
	private Integer id;
	
	private String title;
	
	private String value;
	
	private String category;
	
	@ManyToOne(
			fetch = FetchType.EAGER,
			cascade = CascadeType.ALL
		)
	@JoinColumn(
			name = "levelRequest_id"
			//insertable = false,
			//updatable = false
		)
	private LevelRequest levelRequest;
	
	@JsonIgnore
	@OneToMany(
			mappedBy="fieldRequest",
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER, 
			orphanRemoval = true
		)
	private List<ValidationRequest> validationRequest;
	
	private boolean isValidation;
	
	private boolean isActive;
	
	private String type;
	
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
