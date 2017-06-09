package com.andersonau.implementationLogic.GenerateNumbers;

public class MimicOriginalNewTileRNG implements RandomNumberGenerator{

	private static final int[] _1to4Odds=
	/*5*/	{10,  6,  4,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 0};
	private static final int[][] oddsMap={
	/*1*/	_1to4Odds,
	/*2*/	_1to4Odds,
	/*3*/	_1to4Odds,
	/*4*/	_1to4Odds,
	/*5*/	{ 8,  8,  6,  4,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0, 0},
	/*6*/	{10,  9,  8,  6,  2,  1,  0,  0,  0,  0,  0,  0,  0,  0, 0},
	/*7*/	{ 6,  8,  8,  5,  9,  1,  0,  0,  0,  0,  0,  0,  0,  0, 0},
	/*8*/	{ 3,  3,  2,  2,  3,  1,  1,  0,  0,  0,  0,  0,  0,  0, 0},
	/*9*/	{ 2,  2,  2,  3,  3,  1,  1,  1,  0,  0,  0,  0,  0,  0, 0},
	/*A*/	{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  0,  0,  0,  0, 0},
	/*B*/	{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  0,  0,  0, 0},
	/*C*/	{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  0,  0, 0},
	/*D*/	{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  0, 0},
	/*E*/	{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 0},
	/*F*/	{ 1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 1},
	};     // 1,  2,  3,  4,  5,  6,  7,  8,  9,  A,  B,  C,  D,  E, F
	private static final int[] sums=new int[oddsMap.length];
	
	static {
	//this is done so I can know the total size before hand and can use an array, instead of 
	//appending to a Collections object, and allows me to avoid autoboxing
	//static initializer instead of constructor because of the REST architecture
		for(int i=0;i<oddsMap.length;i++){
			for(int j=0;j<oddsMap[i].length;j++){
				sums[i]+=oddsMap[i][j];
			}
		}
		System.out.println("initializer run");
	}
	
	@Override
	public int next(int[][] gridNumbers) {
		int max=0;
		for(int i=0;i<gridNumbers.length;i++){
			for(int j=0;j<gridNumbers[i].length;j++){
				if(max<gridNumbers[i][j]){
					max=gridNumbers[i][j];
				}
			}
		}
		max--;//account for game being 1 based
		if(max>=oddsMap.length){
			return max=oddsMap.length-1;
		}
		int[] oddsSet=oddsMap[max];
		//unpack condensed odds from number of that index to duplicates of that index
		//this effectively give the numbers weights to be more or less likely to be picked
		int[] flatten=new int[sums[max]];
		int ndx=0;
		for(int i=0;i<oddsSet.length;i++){
			for(int j=0;j<oddsSet[i];j++){
				flatten[ndx]=i+1;
				ndx++;
			}
		}
		int chosen=((int)(Math.random()*1000)%flatten.length);
        return flatten[chosen];
	}
}
