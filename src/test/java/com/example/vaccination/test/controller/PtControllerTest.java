package com.example.vaccination.test.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.validation.BindingResult;


import com.example.vaccination.controller.PtController;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@ExtendWith(MockitoExtension.class)
class PtControllerTest 
{
	public static final Long ID = 1L;
	public static final String PT_NAME = "Saad";
	public static final String PT_FISCAL_CODE = "1234567891234567";
	public static final String PT_VACCSIONATION_NAME = "Modena";
	
	PtController ptController;
	
	@Mock
	private PtService ptService;
	
	@Mock
	BindingResult bindingResult;
	
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
		ptOne.setPtName(PT_NAME);
		ptOne.setPtFiscalCode(PT_FISCAL_CODE);
		ptOne.setPtVaccsionationName(PT_VACCSIONATION_NAME);
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
	
	
	@Test
	void testCreatePT() throws NoSuchMethodException
	{
		//given
		PtDto ptDto = getPtDto(PT_FISCAL_CODE);
		given(ptService.createNewPt(any(PtDto.class))).willReturn(ptDto);
		
		// when
		ResponseEntity<Object> ptOne = ptController.createNewPt(ptDto, bindingResult);
		System.out.println(ptOne);
		
		//then
		assertNotNull(ptOne.getBody());
		
		assertEquals(HttpStatus.CREATED, ptOne.getStatusCode());
		assertEquals(ptDto.getClass().getDeclaredMethod("getPtFiscalCode"), Objects.requireNonNull(ptOne.getBody()).getClass().getDeclaredMethod("getPtFiscalCode"));
		then(ptService).should().createNewPt(any(PtDto.class));
		then(ptService).shouldHaveNoMoreInteractions();
	}
	
	private PtDto getPtDto(String ptFiscalCode) {
		// TODO Auto-generated method stub
		PtDto ptDto = new PtDto();
		ptDto.setId(ID);
		ptDto.setPtName(PT_NAME);
		ptDto.setPtFiscalCode(ptFiscalCode);
		ptDto.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		return ptDto;
	}
	

}
