package com.bank.assignment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DenominationType implements Serializable {

	private static final long serialVersionUID = -8960363201414105295L;

	@Id
	@Column(name = "denomination_type_code")
	private String denominationTypeCode;
	
	@Column(name = "description")
	private String description;

}

