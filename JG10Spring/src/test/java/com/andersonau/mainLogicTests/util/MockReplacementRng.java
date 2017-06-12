package com.andersonau.mainLogicTests.util;

import com.andersonau.implementationLogic.GenerateNumbers.RandomNumberGenerator;

public class MockReplacementRng implements RandomNumberGenerator{

	private final int pullFrom;
	public MockReplacementRng(int pullFrom){
		this.pullFrom=pullFrom;
	}
	@Override
	public int next(int[][] gridNumbers) {
		return pullFrom;
	}

}
