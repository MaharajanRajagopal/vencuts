package com.vencuts.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaboUIController {
	
	@GetMapping("/")
	public String getindex() {
		
		return "uploadfile";
	}

}
