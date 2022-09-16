package com.example.vaccination.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PtIdExce extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public PtIdExce(String message) 
	{
		super(message);
	}
}
