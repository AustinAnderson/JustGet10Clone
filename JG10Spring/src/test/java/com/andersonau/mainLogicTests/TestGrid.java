package com.andersonau.mainLogicTests;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.andersonau.connections.ServerResponse;
import com.andersonau.implementationLogic.MainLogic.CellHolder;
import com.andersonau.implementationLogic.MainLogic.Grid;
import com.andersonau.implementationLogic.MainLogic.Transition;
import com.andersonau.mainLogicTests.util.AssertUtils;
import com.andersonau.mainLogicTests.util.MockReplacementRng;

@SuppressWarnings("serial")//not serializing
@RunWith(Parameterized.class)
public class TestGrid {
	//need to keep order that tiles are repopulated implementation detail, and change this to parameterized test
	
	@Parameters(name="{7}")
	public static Collection<Object[]> data(){
		final int INPUT_GRID=2;
		final int EXPECTED_TRANSITIONS=5;
		final int EXPECTED_REPLACE_LIST=6;
		final int CanCombineAfter=0;
		final int CantCombineAfter=1;
		final int Duplicates=2;
		int[][] loseSaveByReplaceTile={
			{2,4,4,3,2},
			{1,5,3,2,3},
			{4,2,5,3,2},
			{1,4,2,4,3},
			{4,1,5,2,1},
		};
		Object[][] paramData=new Object[][]{
			{4,3,null,3,true,null,null,"can combine after"},
			{3,2,null,9,false,null,null,"can't combine after"},
			{2,0,null,3,true,null,null,"bfs potentially duplicate from"},
			{0,2,loseSaveByReplaceTile,3,false,null,new int[]{3},"lose by replace tile"},
			{0,2,loseSaveByReplaceTile,5,true,null,new int[]{5},"saved by replace tile"},
		};
		paramData[CanCombineAfter][INPUT_GRID]=new int[][]{//combine on 4,3
            {1,2,2,1,1},//1,3,3,3,3
            {1,1,2,1,1},//1,1,3,1,1
            {1,1,2,4,1},//1,1,3,1,1
            {1,1,2,2,2},//1,1,3,4,1
            {1,1,1,2,1} //1,1,1,3,1
        };
		paramData[CanCombineAfter][EXPECTED_TRANSITIONS]=new ArrayList<ArrayList<Transition>>(){{
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(0, 1, 2),
                    new CellHolder(0, 2, 2)
                ));
            }});
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(0,2,2),
                    new CellHolder(1,2,2)
                ));
            }});
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(1,2,2),
                    new CellHolder(2,2,2)
                ));
            }});
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(2,2,2),
                    new CellHolder(3,2,2)
                ));
            }});
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(3,4,2),
                    new CellHolder(3,3,2)
                ));
                add(new Transition(
                    new CellHolder(3,2,2),
                    new CellHolder(3,3,2)
                ));
            }});
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(3,3,2),
                    new CellHolder(4,3,2)
                ));
            }});
        }};
        paramData[CanCombineAfter][EXPECTED_REPLACE_LIST]=new int[]{3,3,3,3,3,3,3};
        
        paramData[CantCombineAfter][INPUT_GRID]=new int[][]{
            {1,2,5,2,1},//1,2,9,2,1
            {2,1,4,1,2},//2,1,5,1,2
            {1,3,2,3,1},//1,3,4,3,1
            {2,1,2,1,2},//2,1,2,1,2
            {1,2,1,2,1} //1,2,1,2,1
        };
        paramData[CantCombineAfter][EXPECTED_TRANSITIONS]=new ArrayList<ArrayList<Transition>>(){{
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(2,2,2),
                    new CellHolder(3,2,2)
                ));
            }});
        }};
        paramData[CantCombineAfter][EXPECTED_REPLACE_LIST]=new int[]{9};
		
		paramData[Duplicates][INPUT_GRID]=new int[][]{
			{1,1,2},
			{1,1,2},
			{1,2,2}
		};
		paramData[Duplicates][EXPECTED_TRANSITIONS]=new ArrayList<ArrayList<Transition>>(){{
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(0,1,1),
                    new CellHolder(1,1,1)
                ));
            }});
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(1,1,1),
                    new CellHolder(1,0,1)
                ));
                add(new Transition(
                    new CellHolder(0,0,1),
                    new CellHolder(1,0,1)
                ));
            }});
            add(new ArrayList<Transition>(){{
                add(new Transition(
                    new CellHolder(1,0,1),
                    new CellHolder(2,0,1)
                ));
            }});
        }};
        paramData[Duplicates][EXPECTED_REPLACE_LIST]=new int[]{3,3,3,3};
		return Arrays.asList(paramData);
	}
	
	private final ArrayList<ArrayList<Transition>> expected;
	private final int[] expectedReplaceList;
	private final boolean expectedCombinable;
	private final ServerResponse response;
	
	public TestGrid(int row,int col,int[][] grid, int replaceNum, boolean expectedCanCombine,
			ArrayList<ArrayList<Transition>> expected,int[] expectedReplaceList,String name){
		this.expected=expected;
		this.expectedReplaceList=expectedReplaceList;
		expectedCombinable=expectedCanCombine;
		response=new Grid(new MockReplacementRng(replaceNum),grid).combineOn(row, col);
	}
	@Test
	public void replaceListTest(){
		assertArrayEquals("testing ReplaceList, ",expectedReplaceList,response.getReplaceList());
	}
	@Test
	public void transitionsListTest(){
		if(expected!=null){
			AssertUtils.assertNestedListsEqual("testing transitionsList, ",expected,response.getTransitionList().getTransitionList());
		}
	}
	@Test
	public void canCombineTest(){
		assertEquals("testing if grid can combine after done\n",expectedCombinable,!response.getHasLost());
	}
}
