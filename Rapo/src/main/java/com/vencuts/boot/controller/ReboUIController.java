package com.vencuts.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ReboUIController {
	
	
	 @GetMapping("/")
	    public String index() {
	        return "recordupload";
	    }

}
