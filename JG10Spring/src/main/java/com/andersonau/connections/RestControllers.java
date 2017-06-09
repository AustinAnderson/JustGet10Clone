package com.andersonau.connections;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andersonau.implementationLogic.GenerateNumbers.MimicOriginalNewTileRNG;
import com.andersonau.implementationLogic.GenerateNumbers.PlainGenerator;
import com.andersonau.implementationLogic.MainLogic.Grid;
import com.andersonau.implementationLogic.MainLogic.TransitionList;

@RestController
public class RestControllers{
	
	@RequestMapping(value="/moveOn/{row}/{col}", method=RequestMethod.POST)
	public String moveOn(@PathVariable int row,@PathVariable int col, @RequestBody int[][] gridData){
		return new Grid(new MimicOriginalNewTileRNG(),gridData).combineOn(row, col);
	}
	
	//for if I want to have a call for highlighting and then another for actual combiniation
	@RequestMapping(value="/bfsOn/{row}/{col}", method=RequestMethod.POST)
	public String bfsOn(@PathVariable int row,@PathVariable int col, @RequestBody int[][] gridData){
		//currently doesn't work
		return new Grid(new PlainGenerator(),gridData).bfsOn(row, col);
	}
	
	@RequestMapping(value="/testStraightSerialize", method=RequestMethod.GET)
	public TransitionList test(){
		int[][] testGrid={
			{1,2,2,2,1},
			{1,1,1,2,2},
			{2,2,1,1,1},
			{2,1,1,2,1},
			{2,1,2,2,2}
		};
		return new Grid(new PlainGenerator(),testGrid).combineOnTest(4, 1);
	}
	@RequestMapping(value="/testSerialize", method=RequestMethod.GET)
	public String test2(){
		int[][] testGrid={
			{1,2,2,2,1},
			{1,1,1,2,2},
			{2,2,1,1,1},
			{2,1,1,2,1},
			{2,1,2,2,2}
		};
		return new Grid(new PlainGenerator(),testGrid).combineOn(4, 1);
	}
	
}
