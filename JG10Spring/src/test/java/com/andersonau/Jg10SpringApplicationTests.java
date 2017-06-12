package com.andersonau;

//import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;

import com.andersonau.mainLogicTests.TestGrid;
import com.andersonau.mainLogicTests.TransitionTests.TestTransition;
import com.andersonau.mainLogicTests.cellHolderTests.CellHolderTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CellHolderTest.class,
	TestGrid.class,
	TestTransition.class
})
//@SpringBootTest
public class Jg10SpringApplicationTests {
	/*
	@Test
	public void contextLoads() {
	}
	*/
}
