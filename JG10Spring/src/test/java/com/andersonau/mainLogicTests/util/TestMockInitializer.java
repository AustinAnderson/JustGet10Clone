package com.andersonau.mainLogicTests.util;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestMockInitializer {
	
	@Test
	public void test() {
		int[][] input={
			{0,1,2},
			{3,4,5},
			{6,7,8}
		};
		MockInitializingRng target=new MockInitializingRng(input);
		int[] actual=new int[9];
		for(int i=0;i<9;i++){
			actual[i]=target.next();
		}
		assertArrayEquals(new int[]{0,1,2,3,4,5,6,7,8},actual);
	}
	@Test
	public void testPullMoreThanHave() {
		int[][] input={
			{0,1,2},
			{3,4,5},
			{6,7,8}
		};
		MockInitializingRng target=new MockInitializingRng(input);
		for(int i=0;i<9;i++){
			target.next();
		}
		try{
			target.next();
			fail("exception was not thrown");
		}catch(IllegalArgumentException ex){
			//pass
		}
	}
}
