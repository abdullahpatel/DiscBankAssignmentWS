package com.bank.assignment;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ClientAccount implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String clientAccountNumber;
	private Client clientId;
	private AccountType accountTypeCode;
	private Currency currencyCode;
	private BigDecimal displayBalance;
	private BigDecimal accountLimit;
	
}
