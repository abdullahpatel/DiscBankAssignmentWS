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
public class AccountType implements Serializable {

	private static final long serialVersionUID = 2730299345627217804L;

	@Id
	@Column(name = "account_type_code")
	private String accountTypeCode;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "transactional")
	private boolean transactional;
	
}
