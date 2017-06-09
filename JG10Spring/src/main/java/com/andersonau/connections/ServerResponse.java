package com.andersonau.connections;

import java.util.List;

import com.andersonau.implementationLogic.MainLogic.TransitionList;

public class ServerResponse {
	private final TransitionList transitionList;
	private final List<Integer> replaceList;
	private final boolean hasLost;
	public ServerResponse(TransitionList transitionList, List<Integer> replaceList,boolean lost){
		this.transitionList=transitionList;
		this.replaceList=replaceList;
		hasLost=lost;
	}
	public TransitionList getTransitionList(){
		return transitionList; 
	}
	public List<Integer> getReplaceList(){
		return replaceList;
	}
	public boolean getHasLost(){
		return hasLost;
	}
	
}
