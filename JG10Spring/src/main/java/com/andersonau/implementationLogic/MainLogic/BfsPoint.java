package com.andersonau.implementationLogic.MainLogic;

import java.util.LinkedList;
import java.util.List;

public class BfsPoint {
	public int row;
	public int getRow(){return row;}//for serialization
	public int col;
	public int getCol(){return col;}//for serialization
	
	private final static int UP=0;
	private final static int DOWN=1;
	private final static int LEFT=2;
	private final static int RIGHT=3;
	private final static int DONE=4;
	private int state=UP;
	private boolean[][] sharedVisitedMat=null;
	private BfsPoint(int r,int c,boolean[][] visitedMat){
		row=r;
		col=c;
		sharedVisitedMat=visitedMat;
	}
	public boolean isNextValid(int[][] grid,int nextRow,int nextCol){
		return nextRow>=0&&nextRow<grid.length&&
		       nextCol>=0&&nextCol<grid[nextRow].length&&
		       !sharedVisitedMat[nextRow][nextCol]&&
		       grid[row][col]==grid[nextRow][nextCol];
	}
	public BfsPoint next(int[][] grid){
		BfsPoint toReturn=null;
		int modR=0;
		int modC=0;
		while(state<DONE&&toReturn==null){
                 if(state==UP)   {modR=-1;modC= 0;}
            else if(state==DOWN) {modR= 1;modC= 0;}
            else if(state==LEFT) {modR= 0;modC=-1;}
            else if(state==RIGHT){modR= 0;modC= 1;}
            if(isNextValid(grid,row+modR,col+modC)){
                sharedVisitedMat[row+modR][col+modC]=true;
                toReturn=new BfsPoint(row+modR,col+modC,sharedVisitedMat);
            }
            state++;
		}
		return toReturn;
	}
	@Override
	public String toString(){
		return "(r:"+row+",c:"+col+")";
	}
	
	public static List<BfsPoint> runBfs(int row, int col, int[][] values){
		boolean[][] visiteds=new boolean[values.length][values[0].length];//booleans init to false
		visiteds[row][col]=true;
		int ndx=0;
		List<BfsPoint> visitedPoints=new LinkedList<>();
		visitedPoints.add(new BfsPoint(row,col,visiteds));
		BfsPoint next=null;
		while(ndx<visitedPoints.size()){
			while((next=visitedPoints.get(ndx).next(values))!=null){
				visitedPoints.add(next);
			};
			ndx++;
		}
		return visitedPoints;
	}
}
