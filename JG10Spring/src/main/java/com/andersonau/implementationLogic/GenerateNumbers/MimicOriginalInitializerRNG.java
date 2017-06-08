package com.andersonau.implementationLogic.GenerateNumbers;

public class MimicOriginalInitializerRNG implements InitializerRandomNumberGenerator{
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
		for(int i=0;i<initializer.length;i++){//shuffle by choosing two random indicies and swapping them
			int ndx1=(int)(Math.random()*100)%initializer.length;
			int ndx2=(int)(Math.random()*100)%initializer.length;
			int temp=initializer[ndx2];
			initializer[ndx2]=initializer[ndx1];
			initializer[ndx1]=temp;
		}
	}

	@Override
	public int next() {
		int toReturn=initializer[currentNdx%initializer.length];
		currentNdx++;
		return toReturn;
	}

}
