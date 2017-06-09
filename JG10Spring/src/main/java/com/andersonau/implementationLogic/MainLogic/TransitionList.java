package com.andersonau.implementationLogic.MainLogic;
import java.util.ArrayList;
import java.util.HashSet;
public class TransitionList{
    //has to preserve order
	private ArrayList<ArrayList<Transition>> transitionList=new ArrayList<>();
    private HashSet<CellHolder> uniqueFroms=new HashSet<>();
    public void addTransition(Transition t){
    	if(uniqueFroms.add(t.fromNdxs)){//if we havn't seen this from cell,
    		if(transitionList.size()==0){
    			transitionList.add(new ArrayList<Transition>());
    		}
    		ArrayList<Transition> current=getLast(transitionList);
    		if(current.size()!=0&&getLast(current).toNdxs!=t.toNdxs){
    			current=new ArrayList<Transition>();
    			transitionList.add(current);
    		}
    		current.add(t);
    	}
    }
    private <T> T getLast(ArrayList<T> list){
    	return list.get(list.size()-1);
    }
    public ArrayList<ArrayList<Transition>> getTransitionList(){
    	return transitionList;
    }
    public int size(){
    	int toReturn=0;
    	for(int i=0;i<transitionList.size();i++){
    		toReturn+=transitionList.get(i).size();
    	}
    	return toReturn;
    }
}
