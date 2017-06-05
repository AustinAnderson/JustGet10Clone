package com.andersonau.implementationLogic.MainLogic;
import java.util.LinkedHashMap;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
public class TransitionList{
    //has to preserve order
    private LinkedHashMap<CellHolder,ArrayList<CellHolder>> map=new LinkedHashMap<CellHolder,ArrayList<CellHolder>>();
    private HashSet<CellHolder> uniqueFroms=new HashSet<>();
    public void addTransition(Transition t){
    	if(uniqueFroms.add(t.from)){//if we havn't seen this from cell,
    		if(map.get(t.to)==null){//if first transition with this toCell, create the list
            	map.put(t.to,new ArrayList<CellHolder>());
        	}
        	map.get(t.to).add(t.from);
    	}
    }
    public JsonArray toJson(){
    	JsonArray toReturn=new JsonArray();
        for(CellHolder h: map.keySet()){
        	JsonArray inner=new JsonArray();
        	toReturn.add(inner);
            List<CellHolder> fromList=map.get(h);
            for(int i=0;i<fromList.size();i++){
            	JsonObject transition=new JsonObject();
            	inner.add(transition);
				transition.add("fromNdxs", fromList.get(i).toJson());
				transition.add("toNdxs", h.toJson());
            }
        }
        return toReturn;
    }
    public int size(){
    	int toReturn=0;
    	for(CellHolder h: map.keySet()){
    		toReturn+=map.get(h).size();
    	}
    	return toReturn;
    }
}
