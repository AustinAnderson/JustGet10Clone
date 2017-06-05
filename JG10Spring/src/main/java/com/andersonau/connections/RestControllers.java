package com.andersonau.connections;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andersonau.implementationLogic.GenerateNumbers.PlainGenerator;
import com.andersonau.implementationLogic.MainLogic.Grid;

@RestController
public class RestControllers{
	
	@RequestMapping(value="/moveOn/{row}/{col}", method=RequestMethod.POST)
	public String moveOn(@PathVariable int row,@PathVariable int col, @RequestBody int[][] grid){
		return Grid.continuedGame(new PlainGenerator(), grid).combineOn(row, col);
	}
	
}
