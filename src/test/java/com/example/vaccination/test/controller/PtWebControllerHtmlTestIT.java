package com.example.vaccination.test.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.vaccination.dao.PtDao;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PtWebControllerHtmlTestIT 
{
	public static final Long ID =1L;
	public static final String PT_NAME ="Saad";
	public static final String PT_FISCAL_CODE ="1234567891234567";
	public static final String PT_VACCSIONATION_NAME ="Modena";
	
	@Autowired
	private PtDao ptDao;
	
	@Autowired
	private PtService ptService;

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseUrl;
	
	
	@Before
	void setup() 
	{
		baseUrl = "http://localhost:" + port;
		driver = new HtmlUnitDriver();
		ptDao.deleteAll();
		ptDao.flush();
	}

	@After
	void teardown() 
	{
		driver.quit();
	}

	@Test
	void testHomePage() 
	{
		
		PtDto pt = ptService.createNewPt(new PtDto(null, PT_NAME, PT_FISCAL_CODE, PT_VACCSIONATION_NAME));
		
		driver.get(baseUrl);
		
		assertThat(driver.findElement(By.id("ptFotm")).getText()).
			contains("Saad", "1234567891234567", "Modena", "Edit");
		
		driver.findElement
			(By.cssSelector ("a[href*='/edit/" + pt.getId() + "']"));
	}	
	
}
