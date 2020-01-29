package com.bank.assignment;

import java.io.Serializable;

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
public class Allocation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long atmId;
	private int count;
	private int value;
	private String denominationType;
	@SuppressWarnings("unused")
	private int totalValueForDenomination;
	private int toDispense;
	
	public int getTotalValueForDenomination() {
		return count * value;
		
	}
	
}
