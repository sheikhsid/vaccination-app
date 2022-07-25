package com.example.vaccination.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.example.vaccination.dto.PtDto;
import com.example.vaccination.service.PtService;

@Controller
public class PtWebController {
	
	private static final String MESSAGE = "message";
	private static final String PT = "pt";
	
	@Autowired
	private PtService ptServiceRef;
	
	

	@GetMapping("/")
	public String index(Model model) 
	{
		
		List<PtDto> allPt = ptServiceRef.getAllPt();
		model.addAttribute(PT, allPt);
		model.addAttribute(MESSAGE, "");
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
	
	@GetMapping("/new")
	public String newPT(Model model) {
		
		model.addAttribute(PT, new PtDto());
		model.addAttribute(MESSAGE, "");
		return "edit";
	}
	
	@GetMapping("/edit/{id}")
	public String editPt(@PathVariable long id, Model model) {
		
		PtDto ptById = ptServiceRef.getPtById(id);
		model.addAttribute(PT, ptById);
		model.addAttribute(MESSAGE,
				ptById == null ? "No pt found with id: " + id : "");
		return "edit";
		
	}
	
}
