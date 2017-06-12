package com.andersonau.mainLogicTests.cellHolderTests;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

import com.andersonau.implementationLogic.MainLogic.CellHolder;

public class CellHolderNonParameterizedTests {

	@Test
	public void replaceWithEmptyCell(){
		CellHolder target=new CellHolder(0,2,3);
		CellHolder helper=new CellHolder(2,2,2);
		helper.shiftCellToMe(target);
		assumeTrue("unable to make target cell empty, test invalid",target.getValue()==CellHolder.EMPTY);
		target.replaceCell(3);
		assertEquals("value was not changed ",3,target.getValue());
		
	}
	@Test
	public void replaceWithFullCell(){
		CellHolder target=new CellHolder(0,1,2);
		target.replaceCell(8);
		assertEquals("full CellHolder should have ignored replaceCell call",2,target.getValue());
	}
}
