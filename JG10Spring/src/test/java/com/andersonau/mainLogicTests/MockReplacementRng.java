package com.andersonau.mainLogicTests;

import com.andersonau.implementationLogic.GenerateNumbers.RandomNumberGenerator;

public class MockReplacementRng extends RandomNumberGenerator{

	private final int pullFrom;
	public MockReplacementRng(int pullFrom){
		this.pullFrom=pullFrom;
	}
	@Override
	protected int nextInternal(int max) {
		return pullFrom;
	}

}
