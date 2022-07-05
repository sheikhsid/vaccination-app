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
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.test.web.servlet.MockMvc;

import com.example.vaccination.controller.PtWebController;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@WebMvcTest(controllers = PtWebController.class)
public class PtWebControllerTest {
	
	public static final Long ID =1L;
	public static final String PT_NAME ="Saad";
	public static final String PT_FISCAL_CODE ="1234567891234567";
	public static final String PT_VACCSIONATION_NAME ="Modena";
	
	@Autowired
	private WebClient webClient;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PtService ptService;
	
	private String removeWindowsCR(String s) {
		return s.replace("\r", "");
	}
	
	@Test
	void testStatus200() throws Exception {
	mvc.perform(get("/"))
	.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	void testReturnHomeView() throws Exception {
	ModelAndViewAssert.assertViewName(mvc.perform(get("/"))
	.andReturn()
	.getModelAndView(), "index");
	}
	
	@Test
	void testGetAll_View() throws Exception {
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
	void test_InsertPt() throws Exception {
	mvc.perform(post("/save")
	.param("ptName", "Saad")
	.param("ptFiscalCode", "1234567891234567")
	.param("ptVaccsionationName", "Modena"))
	.andExpect(view().name("redirect:/")); // go back to the main page
	verify(ptService)
	.createNewPt(new PtDto(null, "Saad", "1234567891234567", "Modena"));
	}
	
	@Test
	void test_UpdateExistingPt() throws Exception {
	mvc.perform(post("/save")
	.param("id", "1")
	.param("ptName", "Saad")
	.param("ptFiscalCode", "1234567891234567")
	.param("ptVaccsionationName", "Modena"))
	.andExpect(view().name("redirect:/")); // go back to the main page
	verify(ptService)
	.updatePtById(1L, new PtDto(1L, "Saad", "1234567891234567", "Modena"));
	}
	
	@Test
	public void tesIndexPageTitle() throws Exception {
	HtmlPage page = webClient.getPage("/");
	assertThat(page.getTitleText()).isEqualTo("PT");
	}
	
	@Test
	public void testIndexPageShowPtRecord() throws Exception
	{
		List<PtDto> pt = new ArrayList<PtDto>();
		PtDto ptOne = new PtDto();
		ptOne.setId(1L);
		ptOne.setPtName(PT_NAME);
		ptOne.setPtFiscalCode(PT_FISCAL_CODE);
		ptOne.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		pt.add(ptOne);
	
		when(ptService.getAllPt())
		.thenReturn(pt);
		HtmlPage page = this.webClient.getPage("/");
		assertThat(page.getBody().getTextContent())
		.doesNotContain("No PT");
		HtmlTable table = page.getHtmlElementById("pt_table");
		assertThat(removeWindowsCR(table.asNormalizedText()))
		.isEqualTo(
					"ID	PT_NAME	PT_FISCAL_CODE	PT_VACCSIONATION_NAME\n" +
							"1	Saad	1234567891234567	Modena" 
				);
	}
	
	
	
	
	
}
