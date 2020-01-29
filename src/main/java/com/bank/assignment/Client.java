package com.bank.assignment;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long clientId;
	private String title;
	private String name;
	private String surname;
	
}
