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
public class ClientType implements Serializable {

	private static final long serialVersionUID = 6994145817665575655L;

	@Id
	@Column(name = "client_type_code")
	private String clientTypeCode;
	
	@Column(name = "description")
	private String description;

}
