package com.andersonau.mainLogicTests;

import com.andersonau.implementationLogic.GenerateNumbers.RandomNumberGenerator;

public class MockReplacementRng extends RandomNumberGenerator{

	private final int[] pullFrom;
	private int nextNdx=0;
	public MockReplacementRng(int[] pullFrom){
		this.pullFrom=pullFrom;
	}
	@Override
	protected int nextInternal(int max) {
		int next=pullFrom[nextNdx];
		nextNdx++;
		if(nextNdx>=pullFrom.length){
			nextNdx=0;
		}
		return next;
	}

}
