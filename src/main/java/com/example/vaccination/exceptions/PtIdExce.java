package com.example.vaccination.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PtIdExce extends RuntimeException {
	
	public PtIdExce(String message) 
	{
		super(message);
	}
	
	
}
