package com.andersonau.mainLogicTests;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.andersonau.implementationLogic.MainLogic.Grid;

@RunWith(Parameterized.class)
public class TestGrid {
	//need to keep order that tiles are repopulated implementation detail, and change this to parameterized test
	private final static int[][] canCombineAfter={//combine on 4,3
		{1,2,2,1,1},//1,3,3,3,1
		{1,1,2,1,1},//1,1,3,1,1
		{1,1,2,3,1},//1,1,3,1,1
		{1,1,2,2,1},//1,1,3,3,1
		{1,1,1,2,1} //1,1,1,3,1
	};
	private final static int[] canCombineAfterReplaceList={3};
	private final static String expectedCanCombine=
	
	
	private final static int[][] cantCombineAfter={
		{1,2,5,2,1},//1,2,9,2,1
		{2,1,4,1,2},//2,1,5,1,2
		{1,3,2,3,1},//1,3,4,3,1
		{2,1,2,1,2},//2,1,2,1,2
		{1,2,1,2,1} //1,2,1,2,1
	};
	private final static int[] cantCombineAfterReplaceList={9};
	
	@Parameters(name="{6}")
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][]{
			{4,3,canCombineAfter,canCombineAfterReplaceList,true,"","can combine after"},
			{3,2,cantCombineAfter,cantCombineAfterReplaceList,false,"","can't combine after"},
		});
		
	}
	private String expected;
	private boolean expectedCombinable;
	private int[][] grid;
	private int[] replaceList;
	private int row;
	private int col;
	
	public TestGrid(int row,int col,int[][] grid, int[] replaceList, boolean expectedCanCombine,String expected,String name){
		this.expected=expected;
		this.grid=grid;
		this.replaceList=replaceList;
		this.row=row;
		this.col=col;
		expectedCombinable=expectedCanCombine;
	}
	
	public void testBFS(String expected,Grid grid){
		assertEquals("\ntesting if grid combine produces correct output\n",expected,grid.combineOn(row, col));
		assertEquals("\ntesting if grid can combine after done\n",expectedCombinable,!grid.hasLost());
	}
	@Test
	public void testNewGrid(){
		testBFS(expected,Grid.newGame(5, new MockInitializingRng(grid),new MockReplacementRng(replaceList)));
	}
	@Test
	public void testExistingGrid(){
		testBFS(expected,Grid.continuedGame(new MockReplacementRng(replaceList),grid));
	}
}
