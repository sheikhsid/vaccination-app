package com.example.vaccination.controller;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;

import io.restassured.*;
import io.restassured.response.*;

import com.example.vaccination.dao.PtDao;
import com.example.vaccination.entities.PtEntities;
import com.example.vaccination.service.PtServiceImpl;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PtControllerTestIT 
{
	@Autowired
	PtServiceImpl ptServiceImpl;

	@Autowired
	private PtDao ptDao;

	@Value("${local.server.port}")
	private int port;

	@BeforeEach
	public void setup() 
	{
		RestAssured.port = port;
		ptDao.deleteAll();
		ptDao.flush();
	}

	@Test
	void testCreateNewPt() throws Exception 
	{
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName("Saad");
		ptOne.setPtFiscalCode("1234567891234567");
		ptOne.setPtVaccsionationName("Modena");
		

		Response response = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(ptOne).when()
				.post("/api/new/pt");

		PtEntities saved = response.getBody().as(PtEntities.class);

		
		assertThat(ptDao.findById(saved.getId())).contains(saved);

	}
	
	
	@Test
	void testGetAll() throws Exception 
	{

		// given
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName("Saad");
		ptOne.setPtFiscalCode("1234567891234567");
		ptOne.setPtVaccsionationName("Modena");

		List<PtEntities> allpt = List.of(ptOne);

		ptDao.saveAll(allpt);
		// when
		when().get("/api/allpt");

		assertEquals(1, ptDao.findAll().size());

	}
	
	
	
	
	
	
}
