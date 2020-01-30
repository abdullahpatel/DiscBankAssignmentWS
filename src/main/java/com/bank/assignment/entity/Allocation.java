package com.bank.assignment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Allocation implements Serializable {

	private static final long serialVersionUID = 5819452816591570372L;

	@Id
	@Column(name = "atm_allocation_id")
	private Long atmAllocationId;
	
	@Column(name = "atm_id")
	private Long atmId;
	
	@Column(name = "count")
	private int count;
	
	@OneToOne
	@JoinColumn(name = "denomination_id")
	private Denomination denomination;
	
	@SuppressWarnings("unused")
	private int totalValueForDenomination;
	private int toDispense;
	
	public int getTotalValueForDenomination() {
		return getCount() * getDenomination().getValue();
	}
	
}
