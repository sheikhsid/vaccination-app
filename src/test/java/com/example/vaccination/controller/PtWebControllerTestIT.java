package com.example.vaccination.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.vaccination.dao.PtDao;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PtWebControllerTestIT 
{
	public static final Long ID =1L;
	public static final String PT_NAME ="Saad";
	public static final String PT_FISCAL_CODE ="1234567891234567";
	public static final String PT_VACCSIONATION_NAME ="Modena";
	
	@Autowired
	private PtDao ptDao;
	
	@Autowired
	private PtService ptService;

	@Value("${local.server.port}")
	private int port;

	private WebDriver driver;

	private String baseUrl;
	
	
	@BeforeEach
	public void setup() 
	{
		System.out.println("setting up");
		baseUrl = "http://localhost:" + port;
		driver = new HtmlUnitDriver();
		ptDao.deleteAll();
		ptDao.flush();
		
	}

	@AfterEach
	public void teardown() 
	{
		System.out.println("tearing down");
		driver.quit();
	}

	@Test
	void testHomePage() 
	{
		
		PtDto pt = ptService.createNewPt(new PtDto(null, PT_NAME, PT_FISCAL_CODE, PT_VACCSIONATION_NAME));
		
		        
		driver.get(baseUrl);
		
		assertThat(driver.findElement(By.id("pt_table")).getText()).
			contains("Saad", "1234567891234567", "Modena", "Edit");
		
		driver.findElement
			(By.cssSelector ("a[href*='/edit/" + pt.getId() + "']"));
	}	
	
	@Test
	void testEditPageNewPt() throws Exception {
		driver.get(baseUrl + "/new");

		driver.findElement(By.name("ptName")).sendKeys("ptTwo");
		driver.findElement(By.name("ptFiscalCode")).sendKeys("1234567891231111"); 
		driver.findElement(By.name("ptVaccsionationName")).sendKeys("Modena");
		driver.findElement(By.name("btn_submit")).click();

		assertThat(ptService.getPtById(2L).getPtName())
			.isEqualTo("ptTwo");
	}
	
	@Test
	void testEditPageUpdateEmployee() throws Exception {
		
		PtDto pt = ptService.createNewPt(new PtDto(null, PT_NAME, PT_FISCAL_CODE, PT_VACCSIONATION_NAME));

		driver.get(baseUrl + "/edit/" + pt.getId());

		final WebElement ptName = driver.findElement(By.name("ptName"));
		ptName.clear();
		ptName.sendKeys("ptTwo");
		final WebElement fiscalCode = driver.findElement(By.name("ptFiscalCode"));
		fiscalCode.clear();
		fiscalCode.sendKeys("1234567891231113");
		final WebElement vaccsionationName = driver.findElement(By.name("ptVaccsionationName"));
		vaccsionationName.clear();
		vaccsionationName.sendKeys("xyz");
		driver.findElement(By.name("btn_submit")).click();

		assertThat(ptService.getPtById(1L).getPtName())
		.isEqualTo("ptTwo");
	}

	
}
