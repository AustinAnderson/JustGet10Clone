package com.andersonau.implementationLogic.MainLogic;
public class Transition{
    public CellHolder toNdxs;
    public CellHolder fromNdxs;
    public Transition(CellHolder f, CellHolder t){
        toNdxs=t;
        fromNdxs=f;
    }
    public CellHolder getToNdxs(){
    	return toNdxs;
    }
    public CellHolder getFromNdxs(){
    	return fromNdxs;
    }
    @Override
    public String toString(){
    	return "%"+toNdxs+"%->%"+fromNdxs+"%";
    }
    @Override
    public int hashCode(){
    	return toNdxs.hashCode()^fromNdxs.hashCode();
    }
    @Override
    public boolean equals(Object other){
    	boolean toReturn=false;
    	if(other instanceof Transition){
    		Transition casted=(Transition)other;
    		if(casted.toNdxs.equals(toNdxs)&&casted.fromNdxs.equals(fromNdxs)){
    			toReturn=true;
    		}
    	}
    	return toReturn;
    }
}
