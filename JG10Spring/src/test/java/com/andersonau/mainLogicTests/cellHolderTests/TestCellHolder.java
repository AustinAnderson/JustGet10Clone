package com.andersonau.mainLogicTests.cellHolderTests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CellHolderParameterizedConstructorTest.class,
	CellHolderParameterizedAdjListTest.class,
	CellHolderParameterizedShiftCellToMeTest.class,
	CellHolderNonParameterizedTests.class
})
public class TestCellHolder{
}
