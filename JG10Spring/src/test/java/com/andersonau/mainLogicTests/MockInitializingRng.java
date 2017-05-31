package com.andersonau.mainLogicTests;

import com.andersonau.implementationLogic.GenerateNumbers.RandomNumberGenerator;

public class MockInitializingRng extends RandomNumberGenerator{
	private final int[][] pullFrom;
	private int currentRowIndex=0;
	private int currentColIndex=0;
	public MockInitializingRng(int[][] pullFrom){
		this.pullFrom=pullFrom;
	}
	@Override
	protected int nextInternal(int max) {
		int toReturn=pullFrom[currentRowIndex][currentColIndex];
		currentRowIndex++;
		if(currentRowIndex>=pullFrom.length){
			currentRowIndex=0;
			currentRowIndex++;
			if(currentColIndex>=pullFrom[0].length){
				currentColIndex=0;
			}
		}
		return toReturn;
	}
}
