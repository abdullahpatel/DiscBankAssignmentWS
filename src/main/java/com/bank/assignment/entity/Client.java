package com.bank.assignment.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Client implements Serializable {

	private static final long serialVersionUID = -1468626944568774925L;

	@Id
	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "title")
	private String title;
	
	@Column(name = "name")	
	private String name;
	
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "dob")
	private LocalDate dateOfBirth;
	
	@ManyToOne
	@JoinColumn(name = "client_sub_type_code")
	private ClientSubType clientSubTypeCode;
}
