package com.example.vaccination.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PtNotFoundExce extends RuntimeException
{
	public PtNotFoundExce(String message)
	{
		super(message);
	}

}
