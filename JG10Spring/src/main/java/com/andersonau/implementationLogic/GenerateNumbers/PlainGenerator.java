package com.andersonau.implementationLogic.GenerateNumbers;
public class PlainGenerator implements RandomNumberGenerator, InitializerRandomNumberGenerator{
	
    public int nextInternal(int max){
        return ((int)(Math.random()*100)%(max))+1;
    }

	@Override
	public int next(int[][] gridNumbers) {
		// TODO Auto-generated method stub
		int max=0;
		for(int i=0;i<gridNumbers.length;i++){
			for(int j=0;j<gridNumbers[i].length;j++){
				if(gridNumbers[i][j]>max){
					max=gridNumbers[i][j];
				}
			}
		}
        return ((int)(Math.random()*100)%(max))+1;
	}

	@Override
	public int next() {
        return ((int)(Math.random()*100)%(3))+1;
	}
}
