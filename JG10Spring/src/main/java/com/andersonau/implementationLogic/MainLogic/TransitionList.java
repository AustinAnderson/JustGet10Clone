package com.andersonau.implementationLogic.MainLogic;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class TransitionList{
    //has to preserve order
    private LinkedHashMap<CellHolder,ArrayList<CellHolder>> map=new LinkedHashMap<CellHolder,ArrayList<CellHolder>>();
    public void addTransition(Transition t){
        if(map.get(t.to)==null){
            map.put(t.to,new ArrayList<CellHolder>());
        }
        map.get(t.to).add(t.from);
    }
    public JSONArray toJSON(){
    	JSONArray toReturn=new JSONArray();
        for(CellHolder h: map.keySet()){
        	JSONArray inner=new JSONArray();
        	toReturn.put(inner);
            List<CellHolder> fromList=map.get(h);
            for(int i=0;i<fromList.size();i++){
            	JSONObject transition=new JSONObject();
            	inner.put(transition);
            	try {
					transition.put("fromNdxs", fromList.get(i).toJSON());
					transition.put("toNdxs", h.toJSON());
				} catch (JSONException e) {
					//logError
				}
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
