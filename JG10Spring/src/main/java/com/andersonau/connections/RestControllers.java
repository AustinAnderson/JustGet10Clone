package com.andersonau.connections;


import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andersonau.implementationLogic.GenerateNumbers.MimicOriginalNewTileRNG;
import com.andersonau.implementationLogic.MainLogic.BfsPoint;
import com.andersonau.implementationLogic.MainLogic.Grid;

@RestController
public class RestControllers{
	
	@RequestMapping(value="/moveOn/{row}/{col}", method=RequestMethod.POST)
	public ServerResponse moveOn(@PathVariable int row,@PathVariable int col, @RequestBody int[][] gridData){
		//bfs using transitions
		return new Grid(new MimicOriginalNewTileRNG(),gridData).combineOn(row, col);
	}
	
	//for if I want to have a call for highlighting and then another for actual combination
	@RequestMapping(value="/bfsOn/{row}/{col}", method=RequestMethod.POST)
	public Iterable<BfsPoint> bfsOn(@PathVariable int row,@PathVariable int col, @RequestBody int[][] gridData){
		return BfsPoint.runBfs(row, col, gridData);//simple int bfs
	}
}
