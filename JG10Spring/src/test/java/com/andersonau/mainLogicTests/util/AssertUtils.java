package com.andersonau.mainLogicTests.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import com.andersonau.implementationLogic.MainLogic.CellHolder;

public class AssertUtils{

	public static <T> void assertNestedListsEqual(List<? extends List<T>> expected,List<? extends List<T>> actual){
		assertNestedListsEqual("",expected,actual);
	}
	public static <T> void assertNestedListsEqual(String message,List<? extends List<T>> expected,List<? extends List<T>> actual){
		assertEquals(message+"\n"+"lengths of top level lists are not equal", expected.size(), actual.size());
		for(int i=0;i<expected.size();i++){
			assertEquals(message+"\n"+"lengths of lists["+i+"] are not equal", expected.get(i).size(), actual.get(i).size());
			for(int j=0;j<expected.get(i).size();j++){
				T expectedEl=expected.get(i).get(j);
				T actualEl=actual.get(i).get(j);
				assertEquals(message+"\n"+"for element List["+i+"]["+j+"],",expectedEl,actualEl);
			}
		}
	}
	public static <T> void assertSetEquals(Set<T> expected,Set<T> actual){
		assertSetEquals("",expected,actual);
	}
	public static <T> void assertSetEquals(String message,Set<T> expected,Set<T> actual){
		assertEquals(message+"\n"+"set lengths dont match, ",expected.size(),actual.size());
		for(T element:expected){
			assertTrue(message+"\n"+"actual is missing element "+element+", ",actual.contains(element));
		}
	}
}
