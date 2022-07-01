package com.example.vaccination.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.example.vaccination.dao.*;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.mapper.PtMapper;

@RequiredArgsConstructor
@Service
public class PtServiceImpl implements PtService{
	
	private final PtDao ptDaoRef;
	private static final PtMapper ptMapper = PtMapper.INSTANCE;
	
	public List<PtDto> getAllPt() {
		
		var ptentities = ptDaoRef.findAll();
		return ptMapper.toPtDto(ptentities);
	}

}
