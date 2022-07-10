package com.example.vaccination.dto;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PtDto 
{
	
	@Id
	@GeneratedValue
	private Long id;
	@NotBlank(message = "Pt Name is required")
	private String ptName;
	@NotNull(message = "Fiscal Code is required")
	@Size(min = 16, max = 16, message = "Please use proper 16 Digits Fiscal Code")
	@Column(updatable = false,unique = true)
	private String ptFiscalCode;
	@NotBlank(message = "User Vaccsionation Name is required")
	private String ptVaccsionationName;
	
}
