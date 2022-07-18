package com.example.vaccination.service;

import java.util.*;

import org.springframework.stereotype.Service;


import com.example.vaccination.dao.*;
import com.example.vaccination.dto.*;
import com.example.vaccination.entities.*;
import com.example.vaccination.exceptions.*;
import com.example.vaccination.mapper.PtMapper;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PtServiceImpl implements PtService{
	
	private final PtDao ptDaoRef;
	private static final PtMapper ptMapper = PtMapper.INSTANCE;
	
	public List<PtDto> getAllPt() {
		
		var ptentities = ptDaoRef.findAll();
		return ptMapper.toPtDto(ptentities);
	}

	@Override
	public PtDto createNewPt(PtDto pt) {
		
		PtEntities dtPtEntities = ptMapper.ptDtoToptEntity(pt);
			
		PtEntities savedPtEntities = ptDaoRef.save(dtPtEntities);
        return  ptMapper.ptEntityToptDto(savedPtEntities);
 

	}

	@Override
	public PtDto updatePtById(long ptId, PtDto ptDto) {
		
		Optional<PtEntities> pt = ptDaoRef.findById(ptId);
		if(pt.isPresent())
		{
			var ptOne = pt.get();
			if(!(ptOne.getId().equals(ptDto.getId())))
			{
				throw new PtIdExce("Pt is not updatable, Original: "+ptOne.getId()+" Updating: "+ptDto.getId());
			}
		}
		else
		{
			throw new PtNotFoundExce("Pt :"+ptId+" does not Exists");
		}
		
		var dtPtEntities = ptMapper.ptDtoToptEntity(ptDto);
		dtPtEntities.setId(ptId);
		var savedPtEntities = ptDaoRef.save(dtPtEntities);

        return ptMapper.ptEntityToptDto(savedPtEntities);
	}

		
	public void deleteById(long ptId) 
	{
		ptDaoRef.deleteById(ptId);
	}

	@Override
	public PtDto getPtById(long ptId) {
		
		var pt = ptDaoRef.findById(ptId).orElse(null);
		return ptMapper.ptEntityToptDto(pt);
	}

}
