package com.andersonau.implementationLogic.GenerateNumbers;

public abstract class RandomNumberGenerator{
    final public static int INITIAL_MAX=2;
    
    public int next(int max){
    	if(max<INITIAL_MAX){
    		max=INITIAL_MAX;
    	}
    	return nextInternal(max);
    }
    protected abstract int nextInternal(int max);
    
}