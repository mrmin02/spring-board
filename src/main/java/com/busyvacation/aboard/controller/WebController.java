package com.busyvacation.aboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.busyvacation.aboard.db.repository.PostJpaRepository;

@Controller
public class WebController {
	
	@Autowired  
	PostJpaRepository PostRepository; 
	
	@RequestMapping(value= {"/"})
	public String main(Model model) {
		model.addAttribute("postList",PostRepository.findAll());
		return "index";
	}
}
