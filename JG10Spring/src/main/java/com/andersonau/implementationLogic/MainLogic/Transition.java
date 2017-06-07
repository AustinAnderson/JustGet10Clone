package com.andersonau.implementationLogic.MainLogic;
public class Transition{
    public CellHolder to;
    public CellHolder from;
    public Transition(CellHolder f, CellHolder t){
        to=t;
        from=f;
    }
}
