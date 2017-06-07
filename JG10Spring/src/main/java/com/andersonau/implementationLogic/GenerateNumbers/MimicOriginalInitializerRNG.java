package com.andersonau.implementationLogic.GenerateNumbers;

public class MimicOriginalInitializerRNG extends RandomNumberGenerator{
	int[][] oddsSets={
		{1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,4},
		{1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,3,3,3,4},
		{1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,3,3,4},
		{1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,3,3,3,3,3,4}
	};
	int[] initializer=null;
	int currentNdx=0;
	
	public MimicOriginalInitializerRNG(){
		initializer=oddsSets[(int)(Math.random()*100)%oddsSets.length];
		for(int i=0;i<initializer.length;i++){
			int ndx1=(int)(Math.random()*100)%initializer.length;
			int ndx2=(int)(Math.random()*100)%initializer.length;
			int temp=initializer[ndx2];
			initializer[ndx2]=initializer[ndx1];
			initializer[ndx1]=temp;
		}
	}

	@Override
	protected int nextInternal(int max) {
		int toReturn=initializer[currentNdx%initializer.length];
		currentNdx++;
		return toReturn;
	}

}
