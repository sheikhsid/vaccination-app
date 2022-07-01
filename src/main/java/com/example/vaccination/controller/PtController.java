package com.example.vaccination.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pt/api/")
public class PtController 
{
	
	private final PtService ptServiceRef;

	public PtController(PtService ptservice) {
		
		this.ptServiceRef = ptservice;
	}
	
	@GetMapping("/allpt")
	public List<PtDto> getAllPt() {
		return ptServiceRef.getAllPt();
	}

}
