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
public class CurrencyAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String accountNumber;
	private String currency;
	private BigDecimal currencyBalance;
	private BigDecimal conversionRate;
	private BigDecimal randBalance;
}

