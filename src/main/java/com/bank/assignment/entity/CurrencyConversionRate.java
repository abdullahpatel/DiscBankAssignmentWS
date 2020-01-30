package com.bank.assignment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class CurrencyConversionRate implements Serializable  {

	private static final long serialVersionUID = 8536512686635192991L;

	@Id
	@Column(name = "currency_code")
	private String currencyCode;

	@Column(name = "conversion_indicator")
	private String conversionIndicator;
	
	@Column(name = "rate")
	private BigDecimal rate;
	
}

