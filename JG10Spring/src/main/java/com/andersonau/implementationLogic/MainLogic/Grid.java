package com.andersonau.implementationLogic.MainLogic;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Deque;
import java.util.ArrayDeque;
import com.andersonau.implementationLogic.GenerateNumbers.RandomNumberGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Grid{
    private List<CellHolder> cells;
    private int squareSize;
    private int max=0;
    private final static int UNINITIALIZED_MAX=2;
    private RandomNumberGenerator generator=null;
    public Grid(RandomNumberGenerator rng,int[][] existing){
    	int max=0;
    	for(int i=0;i<existing.length;i++){
    		for(int j=0;j<existing[i].length;j++){
    			if(existing[i][j]>max){
    				max=existing[i][j];
    			}
    		}
    	}
    	int size=existing.length;
    	this.max=max;
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
    public static String newGame(int sideLength,RandomNumberGenerator initializerRng){
    	JsonArray rows=new JsonArray();
    	for(int i=0;i<sideLength;i++){
    		JsonArray cells=new JsonArray();
    		for(int j=0;j<sideLength;j++){
    			cells.add(initializerRng.next(UNINITIALIZED_MAX));
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
    public String combineOn(int i, int j){
    	JsonObject toStringReturn=new JsonObject();
    	
    	Deque<Transition> animate=internalBfsOn(i,j);
        TransitionList tList=new TransitionList();
        while(animate.size()>0){
            Transition out=animate.removeLast();
            tList.addTransition(out);
        }
        toStringReturn.add("transitionList", tList.toJson());
        JsonArray replaceList=new JsonArray();
        for(int k=0;k<tList.size();k++){
            replaceList.add(generator.next(max));
        }
        toStringReturn.add("replaceList", replaceList);
    	return toStringReturn.toString();
    }
    public String bfsOn(int i,int j){
    	JsonArray toStringReturn=new JsonArray();
    	Deque<Transition> animate=internalBfsOn(i,j);
    	while(animate.size()>0){
    		toStringReturn.add(animate.remove().to.toJson());
    	}
    	return toStringReturn.toString();
    }
    private Deque<Transition> internalBfsOn(int i, int j){
    	int newVal=cells.get(flattenNdx(i,j)).getValue()+1;
    	if(newVal>max){
    		max=newVal;
    	}
        Queue<Transition> bfsQ=new LinkedList<>();
        Deque<Transition> animate=new ArrayDeque<>();
        CellHolder dontChange=cells.get(flattenNdx(i,j));
        bfsQ.add(new Transition(dontChange,dontChange));
        CellHolder topNeighbor=null;
        while(bfsQ.peek()!=null&&bfsQ.peek().from!=null){
            if(topNeighbor!=null&&!topNeighbor.beenVisited()&&
                    bfsQ.peek().from.getValue()==topNeighbor.getValue()&&
                    !bfsQ.peek().from.beenVisited()){
                if(!topNeighbor.equals(dontChange)&&topNeighbor.getValue()!=0){
                    bfsQ.add(new Transition(topNeighbor,bfsQ.peek().from));
                }
            }
            topNeighbor=bfsQ.peek().from.nextCellHolder();
            if(topNeighbor==null){
                Transition removed=bfsQ.remove();
                removed.from.setVisited();
                if(!removed.from.equals(dontChange)){
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
