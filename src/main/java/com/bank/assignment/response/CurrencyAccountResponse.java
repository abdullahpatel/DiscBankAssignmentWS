package com.bank.assignment.response;

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
public class CurrencyAccountResponse implements Serializable {

	private static final long serialVersionUID = -7655111929056448013L;
	
	private String accountNumber;
	private String currency;
	private BigDecimal currencyBalance;
	private BigDecimal conversionRate;
	private BigDecimal randBalance;
}

