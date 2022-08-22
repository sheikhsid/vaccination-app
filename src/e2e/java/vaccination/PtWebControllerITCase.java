package vaccination;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PtWebControllerITCase 
{
	
	private static int port =
			Integer.parseInt(System.getProperty("server.port", "6061"));
			
	private static String baseUrl = "http://localhost:" + port;
	
	private WebDriver driver;
			
	@BeforeClass
	public static void setupClass() 
	{
			
		// setup Chrome Driver
		WebDriverManager.chromedriver().setup();
		
	}
			
	@Before
	public void setup() 
	{
			
		baseUrl = "http://localhost:" + port;
			
		driver = new ChromeDriver();
		
		System.out.println("e2e setup");
	}
			
	@After
	public void teardown() 
	{
			
		driver.quit();
		System.out.println("e2e teardown");
			
	}
			
	
	@Test
	public void test_InsertPt() 
	{
	
		driver.get(baseUrl);
		
		driver.findElement	
		(By.cssSelector("a[href*='/new")).click();
	
		// fill the form
		driver.findElement(By.name("ptName")).sendKeys("ptTwo");
		driver.findElement(By.name("ptFiscalCode")).sendKeys("1234567891231111"); 
		driver.findElement(By.name("ptVaccsionationName")).sendKeys("Modena");
		driver.findElement(By.name("btn_submit")).click();
	
		
		assertThat(driver.findElement(By.id("pt_table")).getText()).
		contains("ptTwo", "1234567891231111", "Modena");
	}
	

}
