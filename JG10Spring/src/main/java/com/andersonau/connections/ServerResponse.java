package com.andersonau.connections;

import com.andersonau.implementationLogic.MainLogic.TransitionList;

public class ServerResponse {
	private final TransitionList transitionList;
	private final int[] replaceList;
	private final boolean hasLost;
	public ServerResponse(TransitionList transitionList, int[] replaceList,boolean lost){
		this.transitionList=transitionList;
		this.replaceList=replaceList;
		hasLost=lost;
	}
	public TransitionList getTransitionList(){
		return transitionList; 
	}
	public int[] getReplaceList(){
		return replaceList;
	}
	public boolean getHasLost(){
		return hasLost;
	}
	
}
