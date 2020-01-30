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
public class Currency implements Serializable  {

	private static final long serialVersionUID = -6517743629713395071L;

	@Id
	@Column(name = "currency_code")
	private String currencyCode;

	@Column(name = "decimal_places")
	private Long decimalPlaces;
	
	@Column(name = "description")
	private String description;
	
	
	private String conversionIndicator;
	private BigDecimal rate;

}
