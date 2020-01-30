package com.bank.assignment.response;

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
public class DispensingNotesResponse implements Serializable {

	private static final long serialVersionUID = 7610282257442269082L;
	
	private int noteAmount;
	private int toDispense;
	
}
