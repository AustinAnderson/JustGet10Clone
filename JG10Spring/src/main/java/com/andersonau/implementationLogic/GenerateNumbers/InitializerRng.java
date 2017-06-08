package com.andersonau.implementationLogic.GenerateNumbers;

public class InitializerRng implements InitializerRandomNumberGenerator {
	
	int[] odds={1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,4};
	@Override
	public int next() {
        return odds[((int)(Math.random()*100)%(25))];
	}

}
