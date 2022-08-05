package com.example.vaccination.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.hamcrest.core.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.validation.*;


import com.example.vaccination.controller.PtController;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.exceptions.PtNotFoundExce;
import com.example.vaccination.service.PtService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private final ObjectMapper mapper = new ObjectMapper();
	
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
		
		this.mockMvc.perform(get("/api/allpt").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].ptName", is("Saad")))
				.andExpect(jsonPath("$[0].ptFiscalCode", is("1234567891234567")))
				.andExpect(jsonPath("$[0].ptVaccsionationName", is("Modena")));
	}
	
	@Test
	void testGetAllNull() throws Exception {
		this.mockMvc.perform(get("/api/allpt").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
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

	@Test
	void testCreatePTOk() throws Exception
	{
		
		PtDto ptDto = getPtDto(PT_FISCAL_CODE);
		
		//given
		given(ptService.createNewPt(any(PtDto.class))).willReturn(ptDto);
		
		//when
		mockMvc.perform(post("/api/new/pt").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(ptDto)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.ptName", equalTo(PT_NAME)))
		.andExpect(jsonPath("$.ptFiscalCode", equalTo(PT_FISCAL_CODE)))
		.andExpect(jsonPath("$.ptVaccsionationName", equalTo(PT_VACCSIONATION_NAME)));
	}
	
	@Test
	void testCreatePT400() throws Exception
	{
		//given
		PtDto ptDto = new PtDto();
		ptDto.setId(ID);
		ptDto.setPtName(PT_NAME);
		ptDto.setPtFiscalCode("123P");
		ptDto.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		//when
		mockMvc.perform(post("/api/new/pt").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(ptDto)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.ptFiscalCode", Is.is("Please use proper 16 Digits Fiscal Code")));
		
	}
	
	@Test
	void testCreatePT400WithEmpty() throws Exception
	{
		//given
		PtDto ptDto = new PtDto();
		ptDto.setId(ID);
		
		//when
		mockMvc.perform(post("/api/new/pt").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(ptDto)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.ptName", Is.is("Pt Name is required")))
		.andExpect(jsonPath("$.ptFiscalCode", Is.is("Fiscal Code is required")))
		.andExpect(jsonPath("$.ptVaccsionationName", Is.is("User Vaccsionation Name is required")));
		
		
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
	
	@Test
	void testUpdatePt()
	{
		//given
		PtDto ptDto = new PtDto();
		ptDto.setId(ID);
		ptDto.setPtName(PT_NAME);
		ptDto.setPtFiscalCode(PT_FISCAL_CODE);
		ptDto.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		PtDto rePt = new PtDto();
		rePt.setId(ptDto.getId());
		rePt.setPtName(ptDto.getPtName());
		rePt.setPtFiscalCode(ptDto.getPtFiscalCode());
		rePt.setPtVaccsionationName(ptDto.getPtVaccsionationName());
		given(ptService.updatePtById(anyLong(), any(PtDto.class))).willReturn(rePt);
		
		//when
		var responseEntity = ptController.updatePtById(ID, ptDto);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
	}
	
	
	@Test
	void testUpdatePtOk() throws Exception
	{
		//given
		PtDto ptDto = new PtDto();
		ptDto.setId(ID);
		ptDto.setPtName(PT_NAME);
		ptDto.setPtFiscalCode(PT_FISCAL_CODE);
		ptDto.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		PtDto rePt = new PtDto();
		rePt.setId(ptDto.getId());
		rePt.setPtName(ptDto.getPtName());
		rePt.setPtFiscalCode(ptDto.getPtFiscalCode());
		rePt.setPtVaccsionationName(ptDto.getPtVaccsionationName());
		given(ptService.updatePtById(anyLong(), any(PtDto.class))).willReturn(rePt);
		
		//then
		mockMvc.perform(put("/api/pt/1/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ptDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.ptName", equalTo(PT_NAME)))
				.andExpect(jsonPath("$.ptFiscalCode", equalTo(PT_FISCAL_CODE)))
				.andExpect(jsonPath("$.ptVaccsionationName", equalTo(PT_VACCSIONATION_NAME)));
		then(ptService).should().updatePtById(anyLong(), any(PtDto.class));
		then(ptService).shouldHaveNoMoreInteractions();
	}
	
	
	@Test
	void testUpdatePt400() throws Exception
	{
		//given
		PtDto ptDto = new PtDto();
		ptDto.setId(ID);
		ptDto.setPtName(PT_NAME);
		ptDto.setPtFiscalCode(PT_FISCAL_CODE);
		ptDto.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		//then
		mockMvc.perform(put("/api/pt/pp")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(ptDto)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void testUpdatePt400PtNotFound()
	{
		//given	
		given(ptService.updatePtById(anyLong(), any(PtDto.class)))
		.willThrow(PtNotFoundExce.class);

		//when
		var response= ptController.updatePtById(ID,new PtDto());
		//Then
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
	}
	
	
	@Test
	void testDeletePt() {
		// given
		PtDto ptDto = new PtDto();
		ptDto.setId(ID);
		ptDto.setPtName(PT_NAME);
		ptDto.setPtFiscalCode(PT_FISCAL_CODE);
		ptDto.setPtVaccsionationName(PT_VACCSIONATION_NAME);

		// when
		ptController.deletePt(ptDto.getId());

		// then
		then(ptService).should().deleteById(anyLong());
		then(ptService).shouldHaveNoMoreInteractions();
	}
	
	@Test
	void testDeletePtsOK() throws Exception{
		// given
		PtDto ptDto = new PtDto();
		ptDto.setId(ID);
		ptDto.setPtName(PT_NAME);
		ptDto.setPtFiscalCode(PT_FISCAL_CODE);
		ptDto.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
//		// when
	        mockMvc.perform(delete("/api/1")
	        .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$",equalTo("PT: 1 has been deleted successfully")));
	}

}
