package com.andersonau;

//import org.junit.Test;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;

import com.andersonau.mainLogicTests.SimpleBfsTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
import com.andersonau.mainLogicTests.TestGrid;
import com.andersonau.mainLogicTests.TransitionListTests.TestTransitionList;
import com.andersonau.mainLogicTests.TransitionTests.TestTransition;
import com.andersonau.mainLogicTests.cellHolderTests.TestCellHolder;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestCellHolder.class,
	TestGrid.class,
	TestTransition.class,
	TestTransitionList.class,
	SimpleBfsTest.class
})
//@SpringBootTest
public class Jg10SpringApplicationTests {
	/*
	@Test
	public void contextLoads() {
	}
	*/
}
