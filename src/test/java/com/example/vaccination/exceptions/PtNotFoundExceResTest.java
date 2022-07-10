package com.example.vaccination.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PtNotFoundExceResTest 
{
	@Test
	void getMessage()
	{
		//given
		String message = "here";
		//when
		PtNotFoundExceRes response = new PtNotFoundExceRes(message);
		//then
		assertEquals(message, response.getMessage());
	}
}
