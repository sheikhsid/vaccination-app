package com.example.vaccination.service;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import com.example.vaccination.dao.PtDao;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.entities.PtEntities;
import com.example.vaccination.exceptions.PtIdExce;
import com.example.vaccination.exceptions.PtNotFoundExce;

@ExtendWith(MockitoExtension.class)
class PtServiceImplTest {
	
	@Mock
	private PtDao ptDao;
	
	private PtService ptService;
	
	@BeforeEach
    void setUp() 
	{
		ptService = new PtServiceImpl(ptDao);
		
	}
	
	@Test
	void testGetAll()
	{
		//given
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName("Saad");
		ptOne.setPtFiscalCode("1234567891234567");
		ptOne.setPtVaccsionationName("Modena");
		
		PtDto dtOne = new PtDto();
		dtOne.setId(ptOne.getId());
		dtOne.setPtName(ptOne.getPtName());
		dtOne.setPtFiscalCode(ptOne.getPtFiscalCode());
		dtOne.setPtVaccsionationName(ptOne.getPtVaccsionationName());
		
		//when //then
		when(ptDao.findAll()).thenReturn(new ArrayList<>(Arrays.asList(ptOne)));
		
		// Assert
		assertThat(ptService.getAllPt()).containsExactly(dtOne);
		
		
		
	}
	
	
	@Test
	void testAdd() throws Exception {
		// given
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName("Saad");
		ptOne.setPtFiscalCode("1234567891234567");
		ptOne.setPtVaccsionationName("Modena");

		PtDto dtOne = new PtDto();
		dtOne.setId(ptOne.getId());
		dtOne.setPtName(ptOne.getPtName());
		dtOne.setPtFiscalCode(ptOne.getPtFiscalCode());
		dtOne.setPtVaccsionationName(ptOne.getPtVaccsionationName());

		given(ptDao.save(any(PtEntities.class))).willReturn(ptOne);
		// When
		PtDto dtTwo = ptService.createNewPt(dtOne);

		// then
		assertNotNull(dtTwo);
		assertEquals(1L, dtTwo.getId());
		assertEquals("Saad", dtTwo.getPtName());
		assertEquals("1234567891234567", dtTwo.getPtFiscalCode());
		assertEquals("Modena", dtTwo.getPtVaccsionationName());
		assertNotNull(dtTwo.getId());

	}
	
	
	@Test
	void testAddExcep() {
		// given
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName("Saad");
		ptOne.setPtFiscalCode("1234567891234567");
		ptOne.setPtVaccsionationName("Modena");

		PtDto dtOne = new PtDto();
		dtOne.setId(ptOne.getId());
		dtOne.setPtName(ptOne.getPtName());
		dtOne.setPtFiscalCode(ptOne.getPtFiscalCode());
		dtOne.setPtVaccsionationName(ptOne.getPtVaccsionationName());

		// when
		given(ptDao.save(any(PtEntities.class))).willThrow(PtIdExce.class);
		// then
		assertThrows(PtIdExce.class, () -> ptService.createNewPt(dtOne));
	}
	
	@Test
	void testDelete() throws Exception {
		// given
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName("Saad");
		ptOne.setPtFiscalCode("1234567891234567");
		ptOne.setPtVaccsionationName("Modena");
		// When
		ptService.deleteById(1L);

		// then
		then(ptDao).should().deleteById(anyLong());
		then(ptDao).shouldHaveNoMoreInteractions();
	}
	
	@Test
	void testUpdate() {
		// given
		PtDto dtOne = new PtDto();
		dtOne.setId(1L);
		dtOne.setPtName("Saad");
		dtOne.setPtFiscalCode("1234567891234567");
		dtOne.setPtVaccsionationName("Modena");

		PtEntities ptOne = new PtEntities();
		ptOne.setId(dtOne.getId());
		ptOne.setPtName(dtOne.getPtName());
		ptOne.setPtFiscalCode(dtOne.getPtFiscalCode());
		ptOne.setPtVaccsionationName(dtOne.getPtVaccsionationName());

		PtEntities sv = new PtEntities();
		sv.setId(1L);
		sv.setPtName(dtOne.getPtName());
		sv.setPtFiscalCode(dtOne.getPtFiscalCode());
		sv.setPtVaccsionationName(dtOne.getPtVaccsionationName());
		
		given(ptDao.findById(anyLong())).willReturn(Optional.of(ptOne));
		given(ptDao.save(any(PtEntities.class))).willReturn(sv);

		// When
		PtDto dtTwo = ptService.updatePtById(1L, dtOne);
		// Then
		assertEquals(dtTwo.getPtName(), sv.getPtName());
		assertEquals(dtTwo.getId(), sv.getId());
		assertNotNull(dtTwo.getId());
		assertNotNull(dtTwo.getPtFiscalCode());
		assertEquals(dtTwo.getPtVaccsionationName(), sv.getPtVaccsionationName());
		assertEquals(sv.getPtFiscalCode(), dtTwo.getPtFiscalCode());

		then(ptDao).should().save(any(PtEntities.class));
		then(ptDao).shouldHaveNoMoreInteractions();
		then(ptDao).shouldHaveNoMoreInteractions();

	}
	
	@Test
	void testUpdateThrows() {
		PtDto dtOne = new PtDto();
		assertThrows(PtNotFoundExce.class, () -> ptService.updatePtById(1L, dtOne));
	}
	
	
	@Test
	void testPtUpdateThrowsIdExceptions() {
		// given
		PtDto dtOne = new PtDto();
		dtOne.setId(1L);

		PtEntities ptOne = new PtEntities();
		ptOne.setId(2L);
		given(ptDao.findById(anyLong())).willReturn(Optional.of(ptOne));
		
		//then
		assertThrows(PtIdExce.class,()->ptService.updatePtById(1L, dtOne));
		

	}	
	
	@Test
	void testGetPtByIdFound() {
		// given
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName("Saad");
		ptOne.setPtFiscalCode("1234567891234567");
		ptOne.setPtVaccsionationName("Modena");

		PtDto dtOne = new PtDto();
		dtOne.setId(ptOne.getId());
		dtOne.setPtName(ptOne.getPtName());
		dtOne.setPtFiscalCode(ptOne.getPtFiscalCode());
		dtOne.setPtVaccsionationName(ptOne.getPtVaccsionationName());
		
		// When
		when(ptDao.findById(1L)).thenReturn(Optional.of(ptOne));

		PtDto dtTwo = ptService.getPtById(1L);
		// Then
		assertNotNull(dtTwo);
		assertEquals(1L, dtTwo.getId());
		assertEquals("Saad", dtTwo.getPtName());
		then(ptDao).shouldHaveNoMoreInteractions();
	}

	@Test
	void testGetPtByIdNotFound() {
		// when
		when(ptDao.findById(anyLong()))

		// then
		.thenReturn(Optional.empty());

		// Assert
		assertThat(ptService.getPtById(1)).isNull();
	}

	
	

}
