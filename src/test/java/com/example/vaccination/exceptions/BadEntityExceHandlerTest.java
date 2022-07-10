package com.example.vaccination.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.http.*;


 class BadEntityExceHandlerTest {

	private final String message = "Hello";
	
	BadEntityExceHandler customEntityExceptionHandler = new BadEntityExceHandler();
	
	@Test
    void handleUserNotFoundException(){
//        When
        ResponseEntity<Object> responseEntity = customEntityExceptionHandler.handleUserNotFoundException(new PtNotFoundExce(message));
//         Then
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST,responseEntity.getStatusCode());

    }
	
	

}
