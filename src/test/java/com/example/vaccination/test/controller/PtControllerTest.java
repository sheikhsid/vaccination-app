package com.example.vaccination.test.controller;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;



import com.example.vaccination.controller.PtController;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@ExtendWith(MockitoExtension.class)
class PtControllerTest 
{
	
	PtController ptController;
	
	@Mock
	private PtService ptService;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		ptController = new PtController(ptService);
		mockMvc = MockMvcBuilders.standaloneSetup(ptController).build();
	}
	
	@Test
	void testGetAll() throws Exception {
		// given
		List<PtDto> pt = new ArrayList<PtDto>();
		PtDto ptOne = new PtDto();
		ptOne.setId(1L);
		ptOne.setPtName("Saad");
		ptOne.setPtFiscalCode("1234567891234567");
		ptOne.setPtVaccsionationName("Modena");
		pt.add(ptOne);

		// when // then
		when(ptService.getAllPt()).thenReturn(pt);
		
		this.mockMvc.perform(get("/pt/api/allpt").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].ptName", is("Saad")))
				.andExpect(jsonPath("$[0].ptFiscalCode", is("1234567891234567")))
				.andExpect(jsonPath("$[0].ptVaccsionationName", is("Modena")));
	}
	
	@Test
	void testGetAllNull() throws Exception {
		this.mockMvc.perform(get("/pt/api/allpt").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}
	

}
