package com.andersonau.implementationLogic.GenerateNumbers;

public class PullRandFromBoardNewTileRNG implements RandomNumberGenerator{

	@Override
	public int next(int[][] gridNumbers) {
		int toReturn=0;
		try{
			int[] flattened=new int[gridNumbers.length*gridNumbers[0].length];
			int ndx=0;
			for(int i=0;i<gridNumbers.length;i++){
				for(int j=0;j<gridNumbers[0].length;j++){
					flattened[ndx]=gridNumbers[i][j];
					ndx++;
				}
			}
			toReturn=flattened[((int)(Math.random()*100)%(flattened.length))];
		}catch(ArrayIndexOutOfBoundsException ex){/*swallowed*/}
		return toReturn;
	}

	//4: 1 1 1 2 3 1 1 3 1 1 1 1 1
	//
	
}
