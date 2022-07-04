package com.example.vaccination.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@Controller
public class PtWebController {
	
	@Autowired
	private PtService ptServiceRef;
	
//	@RequestMapping("/")
//	public String welcome(Map<String, Object> model) {
//	model.put("message", "World");
//	return "index";
//	}
	
	@GetMapping("/")
	public String index(Model model) 
	{
		
		List<PtDto> allPt = ptServiceRef.getAllPt();
		model.addAttribute("pt", allPt);
		model.addAttribute("message", "");
		return "index";	
	}
	
	@PostMapping("/save")
	public String savePt(PtDto pt) {
	final Long id = pt.getId();
	if (id == null) {
		ptServiceRef.createNewPt(pt);
	} else {
		ptServiceRef.updatePtById(id, pt);
	}
	return "redirect:/";
	}
}
