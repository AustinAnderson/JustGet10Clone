package com.andersonau.mainLogicTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.andersonau.implementationLogic.MainLogic.BfsPoint;

public class SimpleBfsTest {

	int[][] grid={
		{1,2,2,1,1},
		{1,1,1,2,2},
		{2,2,1,1,1},
		{2,1,1,2,1},
		{2,1,2,2,2},
	};
	@SuppressWarnings("serial")//not serializing
	ArrayList<int[]> expected=new ArrayList<int[]>(){{
		add(new int[]{0,0});
		add(new int[]{1,0});
		add(new int[]{1,1});
		add(new int[]{1,2});
		add(new int[]{2,2});
		add(new int[]{2,3});
		add(new int[]{2,4});
		add(new int[]{3,1});
		add(new int[]{3,2});
		add(new int[]{3,4});
		add(new int[]{4,1});
	}};
	@Test
	public void allPointsReachedStart31() {
		allPointsReached(3,1);
	}
	@Test
	public void allPointsReachedStart00() {
		allPointsReached(0,0);
	}
	public void allPointsReached(int r,int c){
		List<BfsPoint> points=BfsPoint.runBfs(r, c, grid);
		for(int i=0;i<expected.size();i++){
			int currentRow=expected.get(i)[0];
			int currentCol=expected.get(i)[1];
			assertTrue("bfs failed to reach point ("+currentRow+","+currentCol+")",
				points.stream().anyMatch(bfsPoint->bfsPoint.getRow()==currentRow&&bfsPoint.getCol()==currentCol)
			);
		}
	}

}
