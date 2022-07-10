package com.example.vaccination.exceptions;

public class PtNotFoundExceRes {
	private final String message;
	
	public PtNotFoundExceRes(String message)
	{
		this.message = message;
	}
	public String getMessage()
	{
		return message;
	}
}
