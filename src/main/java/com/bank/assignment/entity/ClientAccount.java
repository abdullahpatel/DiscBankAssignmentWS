package com.bank.assignment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ClientAccount implements Serializable {

	private static final long serialVersionUID = -2693859126580264373L;

	@Id
	@Column(name = "client_account_number")
	private String clientAccountNumber;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client clientId;
	
	@OneToOne
	@JoinColumn(name = "account_type_code")
	private AccountType accountTypeCode;
	
	@OneToOne
	@JoinColumn(name = "currency_code")
	private Currency currencyCode;

	@Column(name = "display_balance")
	private BigDecimal displayBalance;
		
}
