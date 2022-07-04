package com.example.vaccination.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.ModelAndViewAssert;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.test.web.servlet.MockMvc;

import com.example.vaccination.controller.PtWebController;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@WebMvcTest(controllers = PtWebController.class)
public class PtWebControllerTest {
	
	public static final Long ID = 1L;
	public static final String PT_NAME = "Saad";
	public static final String PT_FISCAL_CODE = "1234567891234567";
	public static final String PT_VACCSIONATION_NAME = "Modena";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PtService ptService;
	
	@Test
	public void testStatus200() throws Exception {
	mvc.perform(get("/"))
	.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void testReturnHomeView() throws Exception {
	ModelAndViewAssert.assertViewName(mvc.perform(get("/"))
	.andReturn()
	.getModelAndView(), "index");
	}
	
	@Test
	public void testGetAll_View() throws Exception {
	List<PtDto> pt = new ArrayList<PtDto>();
	PtDto ptOne = new PtDto();
	ptOne.setId(1L);
	ptOne.setPtName(PT_NAME);
	ptOne.setPtFiscalCode(PT_FISCAL_CODE);
	ptOne.setPtVaccsionationName(PT_VACCSIONATION_NAME);
	pt.add(ptOne);
	
	when(ptService.getAllPt())
	.thenReturn(pt);
	mvc.perform(get("/"))
	.andExpect(view().name("index"))
	.andExpect(model().attribute("pt",pt))
	.andExpect(model().attribute("message",
			""));
	}
	
	@Test
	public void test_InsertPt() throws Exception {
	mvc.perform(post("/save")
	.param("ptName", "Saad")
	.param("ptFiscalCode", "1234567891234567")
	.param("ptVaccsionationName", "Modena"))
	.andExpect(view().name("redirect:/")); // go back to the main page
	verify(ptService)
	.createNewPt(new PtDto(null, "Saad", "1234567891234567", "Modena"));
	}
	
	@Test
	public void test_UpdateExistingPt() throws Exception {
	mvc.perform(post("/save")
	.param("id", "1")
	.param("ptName", "Saad")
	.param("ptFiscalCode", "1234567891234567")
	.param("ptVaccsionationName", "Modena"))
	.andExpect(view().name("redirect:/")); // go back to the main page
	verify(ptService)
	.updatePtById(1L, new PtDto(1L, "Saad", "1234567891234567", "Modena"));
	}
	
	
	
	
}
