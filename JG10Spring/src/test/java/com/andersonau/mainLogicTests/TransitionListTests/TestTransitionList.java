package com.andersonau.mainLogicTests.TransitionListTests;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.andersonau.implementationLogic.MainLogic.CellHolder;
import com.andersonau.implementationLogic.MainLogic.Transition;
import com.andersonau.implementationLogic.MainLogic.TransitionList;
import com.andersonau.mainLogicTests.util.AssertUtils;

@SuppressWarnings("serial")//not serializing
public class TestTransitionList {

	private final static Transition[][] data={
		{
            new Transition(new CellHolder(1,2,4),new CellHolder(2,2,4)),
            new Transition(new CellHolder(1,2,4),new CellHolder(3,2,4)),
            new Transition(new CellHolder(3,2,4),new CellHolder(2,2,4)),
		},
		{
            new Transition(new CellHolder(1,2,4),new CellHolder(2,2,4)),
            new Transition(new CellHolder(4,2,4),new CellHolder(3,2,4)),
		},
		{}
	};
	@Test
	public void duplicateFromsIgnored() {
		TransitionList target=new TransitionList();
		ArrayList<ArrayList<Transition>> expected=new ArrayList<ArrayList<Transition>>(){{
			add(new ArrayList<Transition>(){{
				add(data[0][0]);
				add(data[0][2]);
			}});
		}};
		for(int i=0;i<data[0].length;i++){
			target.addTransitionAndExecute(data[0][i]);
		}
		AssertUtils.assertNestedListsEqual(expected,target.getTransitionList());
	}
	@Test
	public void groupedByTo() {
		TransitionList target=new TransitionList();
		ArrayList<ArrayList<Transition>> expected=new ArrayList<ArrayList<Transition>>(){{
			add(new ArrayList<Transition>(){{
				add(data[1][0]);
			}});
			add(new ArrayList<Transition>(){{
				add(data[1][1]);
			}});
		}};
		for(int i=0;i<data[1].length;i++){
			target.addTransitionAndExecute(data[1][i]);
		}
		AssertUtils.assertNestedListsEqual(expected,target.getTransitionList());
	}
	public void testGetLastOn(int dataNdx){
		TransitionList target=new TransitionList();
		for(int i=0;i<data[dataNdx].length;i++){
			target.addTransitionAndExecute(data[dataNdx][i]);
		}
		assertEquals(data[dataNdx][data[dataNdx].length-1],target.getLast());
	}
	@Test
	public void getLastWhenFullAndNotGrouped(){
		testGetLastOn(0);
	}
	@Test
	public void getLastWhenFullAndGrouped(){
		testGetLastOn(1);
	}
	@Test(expected=IndexOutOfBoundsException.class)
	public void getLastWhenEmpty(){
		testGetLastOn(2);
	}
}
