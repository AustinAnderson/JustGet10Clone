package com.andersonau.implementationLogic.MainLogic;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Deque;
import java.util.ArrayDeque;
import com.andersonau.implementationLogic.GenerateNumbers.RandomNumberGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Grid{
    private List<CellHolder> cells;
    private int squareSize;
    private int max=0;
    private RandomNumberGenerator generator=null;
    private Grid(int size,int max,RandomNumberGenerator initializerRng,RandomNumberGenerator rng,int[][] existing){
    	this.max=max;
    	generator=rng;
        cells=new ArrayList<>();
        squareSize=size;
        for(int i=0;i<squareSize;i++){
        	for(int j=0;j<squareSize;j++){
        		int toAdd=initializerRng.next(max);
        		if(existing!=null){
        			toAdd=existing[i][j];
        		}
        		cells.add(new CellHolder(i,j,toAdd));
        	}
        }
        for(int i=0;i<squareSize*squareSize;i++){
            setAdjList(cells.get(i),i);
        }
    }
    public static Grid continuedGame(RandomNumberGenerator rng,int[][] currentGrid){
    	int max=0;
    	for(int i=0;i<currentGrid.length;i++){
    		for(int j=0;j<currentGrid[i].length;j++){
    			if(currentGrid[i][j]>max){
    				max=currentGrid[i][j];
    			}
    		}
    	}
    	return new Grid(currentGrid.length,max,rng,rng,currentGrid);
    }
    public static Grid newGame(int size,RandomNumberGenerator initializerRng,RandomNumberGenerator rng){
    	return new Grid(size,0,initializerRng,rng,null);
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
    	JSONObject toStringReturn=new JSONObject();
    	TransitionList tList=bfsCombineOn(i,j);
    	try {
			toStringReturn.put("transitionList", tList.toJSON());
            JSONArray replaceList=new JSONArray();
            for(int k=0;k<tList.size();k++){
                replaceList.put(generator.next(max));
            }
            toStringReturn.put("replaceList", replaceList);
		} catch (JSONException e) {
			//log error
		}
    	return toStringReturn.toString();
    }
    private TransitionList bfsCombineOn(int i, int j){
    	int newVal=cells.get(flattenNdx(i,j)).getValue()+1;
    	if(newVal>max){
    		max=newVal;
    	}
        Queue<Transition> bfsQ=new LinkedList<>();
        Deque<Transition> animate=new ArrayDeque<>();
        TransitionList tList=new TransitionList();
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

        while(animate.size()>0){
            Transition out=animate.removeLast();
            tList.addTransition(out);
        }
        return tList;
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
