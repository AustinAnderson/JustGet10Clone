package com.andersonau.mainLogicTests.cellHolderTests;

import static org.junit.Assert.*;


import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.andersonau.implementationLogic.MainLogic.CellHolder;

@RunWith(Parameterized.class)
public class CellHolderParameterizedConstructorTest {
//need to test adjeceny and has lost stuff
	@Parameters(name="CellHolder(r: {0}, c: {1}, initialValue: {2})")
	public static Collection<Object[]> data(){
		Object[][] data={
			{0,0,2},
			{1,1,0},
			{1,-1,-1},
			{-1,1,3},
			{-1,-1,2}
		};
		return Arrays.asList(data);
	}
	private int row;
	private int col;
	private int initialValue;
	public CellHolderParameterizedConstructorTest(int row,int col,int initialValue){
		this.row=row;
		this.col=col;
		this.initialValue=initialValue;
	}
	
	
	@Test
	public void isEqualTest(){
		CellHolder target1=new CellHolder(row,col,initialValue);
		CellHolder target2=new CellHolder(row,col,initialValue+1);
		assertTrue("cellHolders "+target1+" and "+target2+" should be equal",target1.equals(target2));
		assertTrue("cellHolders "+target2+" and "+target1+" should be equal",target2.equals(target1));
	}
	@Test
	public void NotEqualByRowTest(){
		CellHolder target1=new CellHolder(row,col,initialValue);
		CellHolder target2=new CellHolder(row+1,col,initialValue);
		assertFalse("cellHolders "+target1+" and "+target2+" should not be equal",target1.equals(target2));
		assertFalse("cellHolders "+target2+" and "+target1+" should not be equal",target2.equals(target1));
	}
	@Test
	public void NotEqualByColTest(){
		CellHolder target1=new CellHolder(row,col,initialValue);
		CellHolder target2=new CellHolder(row,col+1,initialValue);
		assertFalse("cellHolders "+target1+" and "+target2+" should not be equal",target1.equals(target2));
		assertFalse("cellHolders "+target2+" and "+target1+" should not be equal",target2.equals(target1));
	}

	@Test
	public void getValueTest(){
		assertEquals(initialValue,new CellHolder(row,col,initialValue).getValue());
	}
	@Test
	public void visitedTest(){
		CellHolder target=new CellHolder(row,col,initialValue);
		assertFalse("expected visited to be false on instantiation", target.beenVisited());
	}
}
