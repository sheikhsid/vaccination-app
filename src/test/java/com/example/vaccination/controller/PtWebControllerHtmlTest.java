package com.example.vaccination.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@WebMvcTest(controllers = PtWebController.class)
class PtWebControllerHtmlTest {
	
	public static final Long ID =1L;
	public static final String PT_NAME ="Saad";
	public static final String PT_FISCAL_CODE ="1234567891234567";
	public static final String PT_VACCSIONATION_NAME ="Modena";
	
	@Autowired
	private WebClient webClient;
	
	@MockBean
	private PtService ptService;
	
	private String removeWindowsCR(String s) {
		return s.replace("\r", "");
	}
	
	
	@Test
	void tesIndexPageTitle() throws Exception {
	HtmlPage page = webClient.getPage("/");
	assertThat(page.getTitleText()).isEqualTo("PT");
	}
	
	@Test
	void testIndexPageShowPtRecord() throws Exception
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
							"1	Saad	1234567891234567	Modena	Edit" 
				);
		page.getAnchorByHref("/edit/1");
	}
	
	@Test
	void testEditPtNotExist() throws Exception {
	//when
		when(ptService.getPtById(1L))
	.thenReturn(null);
	
	HtmlPage page = this.webClient.getPage("/edit/1");
	
	assertThat(page.getBody().getTextContent())
	.contains("No pt found with id: 1");
	}
	
	@Test
	void testEditPtExist() throws Exception {
		
		PtDto ptOne = new PtDto();
		ptOne.setId(1L);
		ptOne.setPtName(PT_NAME);
		ptOne.setPtFiscalCode(PT_FISCAL_CODE);
		ptOne.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		PtDto ptTwo = new PtDto();
		ptTwo.setId(1L);
		ptTwo.setPtName("saad2");
		ptTwo.setPtFiscalCode("1234567891234888");
		ptTwo.setPtVaccsionationName("Pfizer");
		
		when(ptService.getPtById(1))
			.thenReturn(ptOne);

		HtmlPage page = this.webClient.getPage("/edit/1");
		
		//get the form with the name of ptFotm
		final HtmlForm form = page.getFormByName("ptFotm");
		
		//update existing value with new value
		form.getInputByValue(PT_NAME).setValueAttribute("saad2");
		form.getInputByValue(PT_FISCAL_CODE).setValueAttribute("1234567891234888");
		form.getInputByValue(PT_VACCSIONATION_NAME).setValueAttribute("Pfizer");

		form.getButtonByName("btn_submit").click();

		// verify 
		verify(ptService)
			.updatePtById(1L, ptTwo);
	}
	
	@Test
	void testEditNewPT() throws Exception {
		
		PtDto ptOne = new PtDto();
		ptOne.setId(1L);
		ptOne.setPtName(PT_NAME);
		ptOne.setPtFiscalCode(PT_FISCAL_CODE);
		ptOne.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		HtmlPage page = this.webClient.getPage("/new");

		//get the form with the name of ptFotm
		final HtmlForm form = page.getFormByName("ptFotm");
		form.getInputByName("ptName").setValueAttribute(PT_NAME);
		form.getInputByName("ptFiscalCode").setValueAttribute(PT_FISCAL_CODE);
		form.getInputByName("ptVaccsionationName").setValueAttribute(PT_VACCSIONATION_NAME);		
		form.getButtonByName("btn_submit").click();
		// verify
		verify(ptService)
			.createNewPt(new PtDto(null, PT_NAME, PT_FISCAL_CODE, PT_VACCSIONATION_NAME));
	}
	
	@Test
	void testHomePageProvideALinkForCreatingANewPT() throws Exception {
		HtmlPage page = this.webClient.getPage("/");

		assertThat(
			page
				.getAnchorByText("New PT")
				.getHrefAttribute()
		).isEqualTo("/new");
	}
}
