package com.andersonau.mainLogicTests.util;

import com.andersonau.implementationLogic.GenerateNumbers.InitializerRandomNumberGenerator;

public class MockInitializingRng implements InitializerRandomNumberGenerator{
	private final int[][] pullFrom;
	private int currentRowIndex=0;
	private int currentColIndex=0;
	private int next;
	private boolean empty=false;
	public MockInitializingRng(int[][] pullFrom){
		this.pullFrom=pullFrom;
		next=pullFrom[currentColIndex][currentRowIndex];
	}
	@Override
	public int next() {
		if(empty){
			throw new IllegalArgumentException("all tiles should be initialized");
		
		}
		next=pullFrom[currentColIndex][currentRowIndex];
		currentRowIndex++;
		if(currentRowIndex>=pullFrom[currentColIndex].length){
			currentRowIndex=0;
			currentColIndex++;
			if(currentColIndex>=pullFrom.length){
				empty=true;
			}
		}
		return next;
	}
}
