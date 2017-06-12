package com.andersonau.connections;

import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.andersonau.implementationLogic.GenerateNumbers.MimicOriginalInitializerRNG;
import com.andersonau.implementationLogic.MainLogic.Grid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class ContentControllers {

	@RequestMapping(value="/JustGet10",method=RequestMethod.GET)
	public ModelAndView getPage(){
		ModelAndView indexPage=new ModelAndView("index");
		int[][] data=Grid.newGame(5, new MimicOriginalInitializerRNG());
		try {
			indexPage.addObject("gridNums",new ObjectMapper().writeValueAsString(data));
		} catch (JsonProcessingException e) {
			indexPage.addObject("gridNums","[[1,1,1,1,1],[1,1,1,1,1],[1,1,1,1,1],[1,1,1,1,1],[1,1,1,1,1]]");
		}
		return indexPage;
	}
}
