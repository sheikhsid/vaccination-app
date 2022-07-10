package com.example.vaccination.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class PtIdExceResTest 
{
	static final Long MESSAGE = 1L;
	
	@Test
	void getUserMatricola()
	{
		//when
		PtIdExceRes response = new PtIdExceRes(MESSAGE);
		//then
		assertEquals(MESSAGE, response.getId());
	}
}
