package com.bank.assignment.entity;

import java.io.Serializable;

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
public class ClientSubType implements Serializable {

	private static final long serialVersionUID = 4127966025062825907L;

	@Id
	@Column(name = "client_sub_type_code")
	private String clientSubTypeCode;
	
	@ManyToOne
	@JoinColumn(name = "client_type_code")
	private ClientType clientTypeCode;
	
	@Column(name = "description")
	private String description;

}
