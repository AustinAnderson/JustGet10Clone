package com.andersonau.mainLogicTests;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.andersonau.implementationLogic.MainLogic.Grid;
public class TestGrid {
	//need to keep order that tiles are repopulated implementation detail, and change this to parameterized test
	private final static int[][] grid1={//combine on 4,3
		{1,2,2,1,1},//1,3,3,3,1
		{1,1,2,1,1},//1,1,3,1,1
		{1,1,2,3,1},//1,1,3,1,1
		{1,1,2,2,1},//1,1,3,3,1
		{1,1,1,2,1} //1,1,1,3,1
	};
	private final static int[] replaceList1={3};
	private final static int[][] grid2={
		{1,2,2,1,1},//1,3,3,3,1
		{4,3,2,1,1},//1,1,3,1,1
		{7,9,2,3,1},//1,1,3,1,1
		{1,1,2,2,1},//1,1,3,3,1
		{1,1,1,2,1} //1,1,1,3,1
	};
	
	public void testBFS(String expected,Grid grid){
		assertEquals(expected,grid.combineOn(i, j))
	}
	@Test
	public void testCombineNewGrid(){
		testBFS("",Grid.newGame(5, new MockInitializingRng(grid1),new MockReplacementRng(new int[]{3})));
	}
	@Test
	public void testCombineExistingGrid(){
		Grid.continuedGame(, currentGrid)
		testBFS("",Grid.continuedGame(new MockInitializingRng(grid1), new MockReplacementRng(new int[]{3}),grid1);
	}
}
