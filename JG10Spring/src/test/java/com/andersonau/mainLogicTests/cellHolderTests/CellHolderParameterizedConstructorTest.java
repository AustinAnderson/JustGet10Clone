package com.andersonau.mainLogicTests.cellHolderTests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.andersonau.implementationLogic.MainLogic.CellHolder;

@RunWith(Parameterized.class)
public class CellHolderParameterizedConstructorTest {

	@Parameters(name="new CellHolder(x: {0}, y: {1}, initialValue: {2}).toJSON()=={3}")
	public static Collection<Object[]> data(){
		Object[][] bob={
			{0,0,2,null},
			{1,1,0,null},
			{1,-1,-1,null},
			{-1,1,3,null},
			{-1,-1,2,null}
		};
		for(int i=0;i<bob.length;i++){
			JSONObject expected=new JSONObject();
			try {
				expected.put("x",bob[i][0]);
				expected.put("y",bob[i][1]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bob[i][3]=expected.toString();
		}
		return Arrays.asList(bob);
	}
	private int x;
	private int y;
	private int initialValue;
	private String expected;
	public CellHolderParameterizedConstructorTest(int i1,int i2,int i3,String expected){
		x=i1;
		y=i2;
		initialValue=i3;
		this.expected=expected;
	}
	
	
	@Test
	public void testToJSON() {
		assertEquals(expected,new CellHolder(x,y,initialValue).toJSON().toString());
	}

	@Test
	public void testGetValue(){
		assertEquals(initialValue,new CellHolder(x,y,initialValue).getValue());
	}
	@Test
	public void visited(){
		CellHolder target=new CellHolder(x,y,initialValue);
		assertFalse("expected visited to be false on instantiation", target.beenVisited());
	}
}