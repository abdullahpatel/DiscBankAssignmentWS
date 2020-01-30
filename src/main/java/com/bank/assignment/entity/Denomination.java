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
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Denomination implements Serializable {

	private static final long serialVersionUID = -5171198923973517174L;

	@Id
	@Column(name = "client_id")
	private Long clientId;
	
	@Column(name = "value")
	private int value;
	
	@ManyToOne
	@JoinColumn(name = "denomination_type_code")
	private DenominationType denominationType;

}
