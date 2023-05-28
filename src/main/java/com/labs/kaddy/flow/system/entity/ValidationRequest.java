/**
 * 
 */
package com.labs.kaddy.flow.system.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
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
@Entity
@ToString(
		exclude = {"fieldRequest"}
	)
public class ValidationRequest implements Serializable {

	private static final long serialVersionUID = 2525256094448959036L;
	
	@Id
	@SequenceGenerator(
			name = "validationrequest_sequence",
			sequenceName = "validationrequest_sequence",
			allocationSize = 1
		)
	@GeneratedValue(
			//strategy = GenerationType.IDENTITY
			strategy = GenerationType.SEQUENCE,
			generator = "validationrequest_sequence"
		)
	private Integer id;
	
	private String title;
	
	private String value;
	
	private String type;
	
	@ManyToOne(
			fetch = FetchType.EAGER,
			cascade = CascadeType.ALL
		)
	@JoinColumn(
			name = "fieldRequest_id"
			//insertable = false,
			//updatable = false
		)
	private FieldRequest fieldRequest;
	
	private boolean isActive;
	
	private boolean isEnforce;
	
	private boolean isVisibility;
	
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
