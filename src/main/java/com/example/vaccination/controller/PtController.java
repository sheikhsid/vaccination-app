package com.example.vaccination.controller;

import java.util.*;

import javax.validation.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;


import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/api/")
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
	
	
	
	@PostMapping(value = "/new/pt", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> createNewPt(@Valid @RequestBody PtDto ptDto, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			Map<String, String> errorsMap = new HashMap<>();
			bindingResult.getFieldErrors()
					.forEach(fieldError -> errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
			return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
		}
		
		log.debug("created successfully " + ptDto.getPtFiscalCode());
		return new ResponseEntity<>(ptServiceRef.createNewPt(ptDto), HttpStatus.CREATED);
	}

}
