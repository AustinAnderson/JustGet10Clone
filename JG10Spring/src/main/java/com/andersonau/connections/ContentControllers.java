package com.andersonau.connections;

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

	@RequestMapping(value="/getABetterBrowser",method=RequestMethod.GET)
	public String getBrowserListPage(){
		return "getABetterBrowser";
	}
	@RequestMapping(value="/JustGet10",method=RequestMethod.GET)
	public ModelAndView getPage(){
		ModelAndView indexPage=new ModelAndView("index");
		int[][] data=Grid.newGame(5, new MimicOriginalInitializerRNG());
			//data=new int[][]{{2,3,3,1,2},{1,2,1,2,1},{2,1,2,1,2},{1,2,1,2,1},{2,1,2,1,2}};
			//data=new int[][]{{1,2,3,4,5},{6,7,8,9,10},{11,12,13,1,2},{1,2,1,2,1},{2,1,2,1,2}};
		try {
			indexPage.addObject("gridNums",new ObjectMapper().writeValueAsString(data));
		} catch (JsonProcessingException e) {
			indexPage.addObject("gridNums","[[1,1,1,1,1],[1,1,1,1,1],[1,1,1,1,1],[1,1,1,1,1],[1,1,1,1,1]]");
		}
		return indexPage;
	}
}
