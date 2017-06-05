package com.andersonau.connections;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.andersonau.implementationLogic.GenerateNumbers.PlainGenerator;
import com.andersonau.implementationLogic.MainLogic.Grid;


@Controller
public class ContentControllers {

	@RequestMapping(value="/JustGet10",method=RequestMethod.GET)
	public ModelAndView getPage(){
		ModelAndView indexPage=new ModelAndView("index");
		int[][] grid=new int[5][];
		for(int i=0;i<5;i++){
			grid[i]=new int[5];
		}
		indexPage.addObject("gridNums",Grid.newGame(5, new PlainGenerator()));
		return indexPage;
	}
}
