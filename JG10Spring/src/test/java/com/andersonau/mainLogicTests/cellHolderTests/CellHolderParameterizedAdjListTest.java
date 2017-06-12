package com.andersonau.mainLogicTests.cellHolderTests;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.andersonau.implementationLogic.MainLogic.CellHolder;

@RunWith(Parameterized.class)
public class CellHolderParameterizedAdjListTest{

	private final static int combineOnVal=1;
	@Parameters(name="{0};  {1};  {2};  {3}")
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][]{
			{new CellHolder(0,-1,combineOnVal),new CellHolder(-1,0,combineOnVal),new CellHolder(0,1,combineOnVal),new CellHolder(1,0,combineOnVal),true},
			{new CellHolder(0,-1,combineOnVal),null,new CellHolder(0,1,combineOnVal),null,true},
			{new CellHolder(0,-1,4),new CellHolder(-1,0,2),new CellHolder(0,1,8),new CellHolder(1,0,3),false},
			{null,null,null,null,false},
			{new CellHolder(0,-1,0),new CellHolder(-1,0,4),new CellHolder(0,1,combineOnVal),null,true},
			{new CellHolder(0,-1,8),new CellHolder(-1,0,9),null,new CellHolder(1,0,combineOnVal),true}
		});
	}
	private CellHolder target;
	private ArrayList<CellHolder> expectedNextSequence=new ArrayList<>();
	private boolean expectedCombinable;
	public CellHolderParameterizedAdjListTest(CellHolder left,CellHolder right, CellHolder up, CellHolder down,boolean combinable){
		expectedCombinable=combinable;
		target=new CellHolder(0,0,combineOnVal);
		if(up!=null){
			target.addAdjacentCellHolder(up);
			expectedNextSequence.add(up);
		}
		if(right!=null){
			target.addAdjacentCellHolder(right);
			expectedNextSequence.add(right);
		}
		if(down!=null){
			target.addAdjacentCellHolder(down);
			expectedNextSequence.add(down);
		}
		if(left!=null){
			target.addAdjacentCellHolder(left);
			expectedNextSequence.add(left);
		}
	}
	@Test
	public void getNext(){
		for(int i=0;i<expectedNextSequence.size();i++){
			assertEquals(expectedNextSequence.get(i),target.nextCellHolder());
		}
	}
	@Test
	public void canCombine(){
		assertEquals(expectedCombinable,target.canCombine());
	}
	@Test
	public void canCombineWorksTwice(){
		assertEquals("first run: ",expectedCombinable,target.canCombine());
		assertEquals("second run: ",expectedCombinable,target.canCombine());
	}
	
	
	

}
