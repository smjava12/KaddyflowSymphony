/**
 * 
 */
package com.labs.kaddy.flow.system.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class FieldValidationMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer validationID;
	
	private Integer fieldID;

	private String title;

	private String toolTip;

	private boolean isActive;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "validationID", updatable = false, insertable = false, nullable = true)
	private ValidationRequest validation;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "fieldID", updatable = false, insertable = false, nullable = true)
	private FieldRequest field;

	@Column(name = "createdAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm ss")
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name = "updatedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm ss")
	@UpdateTimestamp
	private Timestamp updatedAt;

}
