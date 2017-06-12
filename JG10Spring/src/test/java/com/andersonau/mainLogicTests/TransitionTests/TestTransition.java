package com.andersonau.mainLogicTests.TransitionTests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.andersonau.implementationLogic.MainLogic.CellHolder;
import com.andersonau.implementationLogic.MainLogic.Transition;

@RunWith(Parameterized.class)
public class TestTransition {

	@Parameters(name="({0}->{1}=={2}->{3})=={4}")
	public static Collection<Object[]> data(){
		CellHolder[] cells={
            new CellHolder(0,0,1),
            new CellHolder(0,1,2),
            new CellHolder(0,1,-1),
            new CellHolder(1,1,-1)
		};
		return Arrays.asList(new Object[][]{
			{cells[0],cells[1],cells[0],cells[1],true},
			{cells[1],cells[0],cells[0],cells[1],false},
			{cells[0],cells[1],cells[1],cells[0],false},
			{cells[0],cells[1],cells[0],cells[2],true},
		});
	}
	private final CellHolder fromOne;
	private final CellHolder toOne;
	private final CellHolder fromTwo;
	private final CellHolder toTwo;
	private final boolean shouldEqual;
	public TestTransition(CellHolder fromOne,CellHolder toOne,CellHolder fromTwo,CellHolder toTwo,boolean shouldEqual){
		this.fromOne=fromOne;
		this.fromTwo=fromTwo;
		this.toOne=toOne;
		this.toTwo=toTwo;
		this.shouldEqual=shouldEqual;
	}
	@Test
	public void equalityTest() {
		assertEquals(".equals failed: ",shouldEqual,new Transition(fromOne,toOne).equals(new Transition(fromTwo,toTwo)));
	}

}
