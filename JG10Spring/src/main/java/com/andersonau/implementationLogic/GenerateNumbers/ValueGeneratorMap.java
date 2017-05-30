package com.andersonau.implementationLogic.GenerateNumbers;

import java.util.HashMap;
import java.util.Map;

public class ValueGeneratorMap{
	
	public static final RandomNumberGenerator getStrategy(String key){
		if(!factoryMap.keySet().contains(key)){
			key=PlainGenerator;
		}
		return factoryMap.get(key);
	}
	public static final String PlainGenerator="Plain";
	
    @SuppressWarnings("serial")//not going to serialize it
	private static final Map<String,RandomNumberGenerator> factoryMap=new HashMap<String,RandomNumberGenerator>(){
		{
			put(PlainGenerator,new PlainGenerator());
		}
    };

}
