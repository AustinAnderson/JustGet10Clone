package com.andersonau.connections;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ContentControllers {

	@RequestMapping(value="/JustGet10",method=RequestMethod.GET)
	public String getPage(){
		return "index.html";
	}
}
