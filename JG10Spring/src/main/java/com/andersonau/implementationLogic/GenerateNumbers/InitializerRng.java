package com.andersonau.implementationLogic.GenerateNumbers;

public class InitializerRng extends RandomNumberGenerator {
	
	int[] odds={1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,4};
	@Override
	protected int nextInternal(int max) {
        return odds[((int)(Math.random()*100)%(25))];
	}

}
