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
public class CellHolderParameterizedShiftCellToMeTest {
//need to test adjeceny and has lost stuff
	@Parameters(name="(r: {0}, c: {1}, init: {2})->("+targetRow+","+targetCol+";"+targetInit+")")
	public static Collection<Object[]> data(){
		Object[][] data={
			{0,0,targetAdjValue,true},
			{1,1,1,false},
			{1,-1,0,false},
			{-1,1,3,false},
			{-1,-1,targetAdjValue,true}
		};
		return Arrays.asList(data);
	}
	private static final int targetRow=1;
	private static final int targetCol=2;
	private static final int targetInit=9;
	private static final int targetAdjValue=targetInit-3;
	private final CellHolder adjacent;
	private final CellHolder shiftTarget;
	private final int row;
	private final int col;
	private final int value;
	private final boolean expectedCombinable;
	public CellHolderParameterizedShiftCellToMeTest(int row,int col,int value,boolean expectedCombinable){
		this.row=row;
		this.col=col;
		this.value=value;
		this.expectedCombinable=expectedCombinable;
		shiftTarget=new CellHolder(targetRow,targetCol,targetInit);
		adjacent=new CellHolder(targetRow+1,targetCol,targetAdjValue);
		shiftTarget.addAdjacentCellHolder(adjacent);
	}
	@Test
	public void shiftDoesntChangeAdjListTest(){
		shiftTarget.shiftCellToMe(new CellHolder(row,col,value));
		assertEquals(adjacent,shiftTarget.nextCellHolder());
	}
	@Test
	public void shiftChangesValueTest(){
		shiftTarget.shiftCellToMe(new CellHolder(row,col,value));
		assertEquals("verify shift cell changes value: ",value,shiftTarget.getValue());
	}
	@Test
	public void shiftChangesCombinableTest(){
		assertEquals("verify not combinable to begin with: ",false,shiftTarget.canCombine());
		shiftTarget.shiftCellToMe(new CellHolder(row,col,value));
		assertEquals("verify is now combinable if it should be: ",expectedCombinable,shiftTarget.canCombine() );
	}
	@Test
	public void shiftDoesntChangeCellHolderPosition(){
		shiftTarget.shiftCellToMe(new CellHolder(row,col,value));
		assertArrayEquals("verify row and col didn't change",new int[]{targetRow,targetCol},new int[]{shiftTarget.getRow(),shiftTarget.getCol()});
	}
	@Test
	public void shiftClearsFromCell(){
		CellHolder from=new CellHolder(row,col,value);
		shiftTarget.shiftCellToMe(from);
		assertEquals("verify from cell got cleared ",CellHolder.EMPTY,from.getValue());
	}
}
