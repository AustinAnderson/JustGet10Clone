package com.andersonau.implementationLogic.GenerateNumbers;
public class PlainGenerator extends RandomNumberGenerator{
	
    @Override
    protected int nextInternal(int max){
        return ((int)(Math.random()*100)%(max))+1;
    }
}
