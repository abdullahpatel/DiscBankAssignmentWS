package com.bank.assignment;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Currency implements Serializable  {

	private static final long serialVersionUID = 1L;
	
	private String currencyCode;
	private String conversionIndicator;
	private BigDecimal rate;

}
