package com.bank.assignment;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountType implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String accountTypeCode;
	private String description;
	private boolean transactional;
	
}
