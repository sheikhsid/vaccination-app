package com.example.vaccination.service;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import com.example.vaccination.dao.PtDao;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.entities.PtEntities;

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

}
