package com.andersonau.mainLogicTests;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.andersonau.implementationLogic.MainLogic.Grid;

@RunWith(Parameterized.class)
public class TestGrid {
	//need to keep order that tiles are repopulated implementation detail, and change this to parameterized test
	private final static int[][] canCombineAfter={//combine on 4,3
		{1,2,2,1,1},//1,3,3,3,3
		{1,1,2,1,1},//1,1,3,1,1
		{1,1,2,4,1},//1,1,3,1,1
		{1,1,2,2,2},//1,1,3,4,1
		{1,1,1,2,1} //1,1,1,3,1
	};
	private final static int canCombineAfterReplaceList=3;
	private static String expectedOutputCanCombine;
	
	private final static int[][] cantCombineAfter={
		{1,2,5,2,1},//1,2,9,2,1
		{2,1,4,1,2},//2,1,5,1,2
		{1,3,2,3,1},//1,3,4,3,1
		{2,1,2,1,2},//2,1,2,1,2
		{1,2,1,2,1} //1,2,1,2,1
	};
	private final static int cantCombineAfterReplaceList=9;
	private static String expectedOutputCantCombine;
	
	@Parameters(name="{6}")
	public static Collection<Object[]> data(){
		try{
            expectedOutputCanCombine=new JSONObject()
                .put("replaceList",new JSONArray().put(3).put(3).put(3).put(3).put(3).put(3).put(3))
                .put("transitionList",new JSONArray()
                    .put(new JSONArray()
                        .put(new JSONObject()
                            .put("fromNdxs", new JSONObject().put("row", 0).put("col", 1))
                            .put("toNdxs", new JSONObject().put("row", 0).put("col", 2))
                        )
                    )
                    .put(new JSONArray()
                        .put(new JSONObject()
                            .put("fromNdxs", new JSONObject().put("row", 0).put("col", 2))
                            .put("toNdxs", new JSONObject().put("row", 1).put("col", 2))
                        )
                    )
                    .put(new JSONArray()
                        .put(new JSONObject()
                            .put("fromNdxs", new JSONObject().put("row", 1).put("col", 2))
                            .put("toNdxs", new JSONObject().put("row", 2).put("col", 2))
                        )
                    )
                    .put(new JSONArray()
                        .put(new JSONObject()
                            .put("fromNdxs", new JSONObject().put("row", 2).put("col", 2))
                            .put("toNdxs", new JSONObject().put("row", 3).put("col", 2))
                        )
                    )
                    .put(new JSONArray()
                        .put(new JSONObject()
                            .put("fromNdxs", new JSONObject().put("row", 3).put("col", 4))
                            .put("toNdxs", new JSONObject().put("row", 3).put("col", 3))
                        )
                        .put(new JSONObject()
                            .put("fromNdxs", new JSONObject().put("row", 3).put("col", 2))
                            .put("toNdxs", new JSONObject().put("row", 3).put("col", 3))
                        )
                    )
                    .put(new JSONArray()
                        .put(new JSONObject()
                            .put("fromNdxs", new JSONObject().put("row", 3).put("col", 3))
                            .put("toNdxs", new JSONObject().put("row", 4).put("col", 3))
                        )
                    )
                )
            .toString();
            System.out.println(expectedOutputCanCombine);
		}catch(Exception ex){
            Assume.assumeTrue("initializing expected can combine after string failed "+ex, false);
		}
		try{
            expectedOutputCantCombine=new JSONObject()
                .put("replaceList",new JSONArray().put(9))
                .put("transitionList",new JSONArray()
                    .put(new JSONArray()
                        .put(new JSONObject()
                            .put("fromNdxs", new JSONObject().put("row", 2).put("col", 2))
                            .put("toNdxs", new JSONObject().put("row", 3).put("col", 2))
                        )
                    )
                )
            .toString();
            System.out.println(expectedOutputCantCombine);
		}catch(Exception ex){
            Assume.assumeTrue("initializing expected can't combine after string failed "+ex, false);
		}
		return Arrays.asList(new Object[][]{
			{4,3,canCombineAfter,canCombineAfterReplaceList,true,expectedOutputCanCombine,"can combine after"},
			{3,2,cantCombineAfter,cantCombineAfterReplaceList,false,expectedOutputCantCombine,"can't combine after"},
		});
	}
	
	private String expected;
	private boolean expectedCombinable;
	private int[][] grid;
	private int replaceNum;
	private int row;
	private int col;
	
	public TestGrid(int row,int col,int[][] grid, int replaceNum, boolean expectedCanCombine,String expected,String name){
		this.expected=expected;
		this.grid=grid;
		this.replaceNum=replaceNum;
		this.row=row;
		this.col=col;
		expectedCombinable=expectedCanCombine;
	}
	
	public void testBFS(String expected,Grid grid){
		String actual=grid.combineOn(row, col);
		
		assertEquals("\ntesting if grid combine produces correct output\n",expected,actual);
		assertEquals("\ntesting if grid can combine after done\n",expectedCombinable,!grid.hasLost());
	}
	@Test
	public void testNewGrid(){
		testBFS(expected,Grid.newGame(5, new MockInitializingRng(grid),new MockReplacementRng(replaceNum)));
	}
	@Test
	public void testExistingGrid(){
		testBFS(expected,Grid.continuedGame(new MockReplacementRng(replaceNum),grid));
	}
}
