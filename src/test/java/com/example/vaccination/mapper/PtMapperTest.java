package com.example.vaccination.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.entities.PtEntities;

class PtMapperTest {
	
	public static final Long ID = 1L;
	public static final String PT_NAME = "Saad";
	public static final String PT_FISCAL_CODE = "1234567891234567";
	public static final String PT_VACCSIONATION_NAME = "Modena";
	
	PtMapper ptMapper = PtMapper.INSTANCE;
	
	@Test
	void ptEntityToptDtoNull()
	{
		assertNull(ptMapper.ptEntityToptDto(null));
	}
	
	@Test
	void ptEntityToptDtoEmpty()
	{
		assertNotNull(ptMapper.ptEntityToptDto(new PtEntities()));
	}
	
	
	@Test
	void ptEntityToptDto()
	{
		// given
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName(PT_NAME);
		ptOne.setPtFiscalCode(PT_FISCAL_CODE);
		ptOne.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		//When
		PtDto ptDto = ptMapper.ptEntityToptDto(ptOne);
		
		//then
		assertNotNull(ptDto);
		assertEquals(ID, ptDto.getId());
		assertEquals(PT_NAME, ptDto.getPtName());
		assertEquals(PT_FISCAL_CODE, ptDto.getPtFiscalCode());
		assertEquals(PT_VACCSIONATION_NAME, ptDto.getPtVaccsionationName());
	}
	
	
	
	@Test
	void ptDtoToptEntityNull()
	{
		assertNull(ptMapper.ptDtoToptEntity(null));
	}
	
	@Test
	void ptDtoToptEntityEmpty()
	{
		assertNotNull(ptMapper.ptDtoToptEntity(new PtDto()));
	}
	
	
	@Test
	void ptDtoToptEntity()
	{
		// given
		PtDto ptDto = new PtDto();
		ptDto.setId(1L);
		ptDto.setPtName(PT_NAME);
		ptDto.setPtFiscalCode(PT_FISCAL_CODE);
		ptDto.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		//When
		PtEntities ptOne = ptMapper.ptDtoToptEntity(ptDto);
		
		//then
		assertNotNull(ptOne);
		assertEquals(ID, ptOne.getId());
		assertEquals(PT_NAME, ptOne.getPtName());
		assertEquals(PT_FISCAL_CODE, ptOne.getPtFiscalCode());
		assertEquals(PT_VACCSIONATION_NAME, ptOne.getPtVaccsionationName());
	}
	
	
	@Test
	void toPtDtoNull()
	{
		assertNull(ptMapper.toPtDto(null));
	}
	
	@Test
	void toPtDtoEmpty()
	{
		List<PtEntities> ptEntities = new ArrayList<>(Arrays.asList());
		assertNotNull(ptMapper.toPtDto(ptEntities));
	}
		
	
	@Test
	void toPtDto()
	{
		// given
		PtEntities ptOne = new PtEntities();
		ptOne.setId(1L);
		ptOne.setPtName(PT_NAME);
		ptOne.setPtFiscalCode(PT_FISCAL_CODE);
		ptOne.setPtVaccsionationName(PT_VACCSIONATION_NAME);
		
		List<PtEntities> ptTwo = new ArrayList<>(Arrays.asList());
		
		//When
		List<PtDto> ptDto = ptMapper.toPtDto(ptTwo);
		
		//then
		assertNotNull(ptDto);
		
	}
}
