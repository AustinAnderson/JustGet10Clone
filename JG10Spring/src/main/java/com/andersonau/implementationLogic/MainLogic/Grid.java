package com.andersonau.implementationLogic.MainLogic;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Deque;
import java.util.HashSet;
import java.util.ArrayDeque;

import com.andersonau.connections.ServerResponse;
import com.andersonau.implementationLogic.GenerateNumbers.InitializerRandomNumberGenerator;
import com.andersonau.implementationLogic.GenerateNumbers.RandomNumberGenerator;
import com.google.gson.JsonArray;

public class Grid{
    private List<CellHolder> cells;
    private int squareSize;
    private RandomNumberGenerator generator=null;
    private final int[][] existing;
    public Grid(RandomNumberGenerator rng,int[][] existing){
    	int size=existing.length;
    	this.existing=existing;
    	generator=rng;
        cells=new ArrayList<>();
        squareSize=size;
        for(int i=0;i<squareSize;i++){
        	for(int j=0;j<squareSize;j++){
        		int toAdd=existing[i][j];
        		cells.add(new CellHolder(i,j,toAdd));
        	}
        }
        for(int i=0;i<squareSize*squareSize;i++){
            setAdjList(cells.get(i),i);
        }
    }
    public static String newGame(int sideLength,InitializerRandomNumberGenerator initializerRng){
    	JsonArray rows=new JsonArray();
    	for(int i=0;i<sideLength;i++){
    		JsonArray cells=new JsonArray();
    		for(int j=0;j<sideLength;j++){
    			cells.add(initializerRng.next());
    		}
    		rows.add(cells);
    	}
    	return rows.toString();
    }

    public boolean hasLost(){//need to test this
        boolean lost=true;
        for(CellHolder currentHolder: cells){
            if(currentHolder.canCombine()){
                lost=false;
            }
        }
        return lost;
    }
    public ServerResponse combineOn(int i, int j){
    	Deque<Transition> animate=internalBfsOn(i,j);
        TransitionList tList=new TransitionList();
        while(animate.size()>0){
            Transition out=animate.removeLast();
            tList.addTransition(out);
        }
        List<Integer> replacements=new ArrayList<>();
        for(int k=0;k<tList.size();k++){
            replacements.add(generator.next(existing));
        }
    	return new ServerResponse(tList,replacements,hasLost());
    }
    public TransitionList combineOnTest(int i, int j){
    	Deque<Transition> animate=internalBfsOn(i,j);
        TransitionList tList=new TransitionList();
        while(animate.size()>0){
            Transition out=animate.removeLast();
            tList.addTransition(out);
        }
        return tList;
    }
    public Set<CellHolder> bfsOn(int i,int j){
    	Set<CellHolder> affectedTiles=new HashSet<>();
    	Deque<Transition> results=internalBfsOn(i,j);
    	for(int k=0;k<results.size();k++){
    		Transition result=results.pop();
    		affectedTiles.add(result.fromNdxs);
    		affectedTiles.add(result.toNdxs);
    	}
    	return affectedTiles;
    }
    private Deque<Transition> internalBfsOn(int i, int j){
        Queue<Transition> bfsQ=new LinkedList<>();
        Deque<Transition> animate=new ArrayDeque<>();
        CellHolder dontChange=cells.get(flattenNdx(i,j));
        bfsQ.add(new Transition(dontChange,dontChange));
        CellHolder topNeighbor=null;
        while(bfsQ.peek()!=null&&bfsQ.peek().fromNdxs!=null){
            if(topNeighbor!=null&&!topNeighbor.beenVisited()&&
                    bfsQ.peek().fromNdxs.getValue()==topNeighbor.getValue()&&
                    !bfsQ.peek().fromNdxs.beenVisited()){
                if(!topNeighbor.equals(dontChange)&&topNeighbor.getValue()!=0){
                    bfsQ.add(new Transition(topNeighbor,bfsQ.peek().fromNdxs));
                }
            }
            topNeighbor=bfsQ.peek().fromNdxs.nextCellHolder();
            if(topNeighbor==null){
                Transition removed=bfsQ.remove();
                removed.fromNdxs.setVisited();
                if(!removed.fromNdxs.equals(dontChange)){
                    animate.addLast(removed);
                }
            }
        }

        return animate;
    }

    private void setAdjList(CellHolder toSet,int index){
        if((index-squareSize)>=0){
            toSet.addAdjacentCellHolder(cells.get(index-squareSize));
        }
        if(index%squareSize!=0){
            toSet.addAdjacentCellHolder(cells.get(index-1));
        }
        if((index+1)%squareSize!=0){
            toSet.addAdjacentCellHolder(cells.get(index+1));
        }
        if((index+squareSize)<(squareSize*squareSize)){
            toSet.addAdjacentCellHolder(cells.get(index+squareSize));
        }
    }
    //for a
    //0 1 2 3 
    //4 5 6 7 
    //8 9 A B
    //C D E F
    //a[2][3]=>a[11]
    private int flattenNdx(int i,int j){
        return i*squareSize+j;
    }
}
