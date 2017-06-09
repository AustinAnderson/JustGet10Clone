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
}
