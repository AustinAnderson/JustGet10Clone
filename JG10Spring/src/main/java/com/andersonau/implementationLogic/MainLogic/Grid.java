package com.andersonau.implementationLogic.MainLogic;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Deque;
import java.util.ArrayDeque;

import com.andersonau.connections.ServerResponse;
import com.andersonau.implementationLogic.GenerateNumbers.InitializerRandomNumberGenerator;
import com.andersonau.implementationLogic.GenerateNumbers.RandomNumberGenerator;

public class Grid{
    private final CellHolder[][] cells;
    private final int squareSize;
    private RandomNumberGenerator generator=null;
    private final int[][] existing;
    public Grid(RandomNumberGenerator rng,int[][] existing){
    	int size=existing.length;
    	this.existing=existing;
    	generator=rng;
        squareSize=size;
        cells=new CellHolder[squareSize][];
        for(int i=0;i<squareSize;i++){
        	cells[i]=new CellHolder[squareSize];
        	for(int j=0;j<squareSize;j++){
        		int toAdd=existing[i][j];
        		cells[i][j]=new CellHolder(i,j,toAdd);
        	}
        }
        for(int i=0;i<squareSize;i++){
        	for(int j=0;j<squareSize;j++){
        		setAdjList(cells[i][j],i,j);
        	}
        }
    }
    public static int[][] newGame(int sideLength,InitializerRandomNumberGenerator initializerRng){
    	int[][] newGameGrid=new int[sideLength][];
    	for(int i=0;i<newGameGrid.length;i++){
    		newGameGrid[i]=new int[sideLength];
    		for(int j=0;j<newGameGrid[i].length;j++){
    			newGameGrid[i][j]=initializerRng.next();
    		}
    	}
    	return newGameGrid;
    }
    
    public ServerResponse combineOn(int i, int j){
    	TransitionList transitionList=internalBfsOn(i,j);
        int[] replacements=new int[transitionList.size()];
        if(transitionList.size()>0){
        	for(int k=transitionList.size()-1;k>=0;k--){
            	replacements[k]=generator.next(existing);
        	}
        	collapseAndRefill(replacements);
        }else{
        	transitionList=null;
        }
    	return new ServerResponse(transitionList,replacements,hasLost());
    }
    private TransitionList internalBfsOn(int i, int j){
        Queue<Transition> bfsQ=new LinkedList<>();
        Deque<Transition> animate=new ArrayDeque<>();
        CellHolder dontChange=cells[i][j];
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
        TransitionList tList=new TransitionList();
        while(animate.size()>0){
            Transition out=animate.removeLast();
            tList.addTransitionAndExecute(out);
        }
        dontChange.increment();
    	return tList;
    }
    private boolean hasLost(){
        boolean lost=true;
        for(int i=0;i<cells.length;i++){
        	for(int j=0;j<cells[i].length;j++){
        		if(cells[i][j].canCombine()){
        			lost=false;
        		}
        	}
        }
        return lost;
    }
    private void collapseAndRefill(int[] replacements){
    	int replaceNdx=replacements.length-1;
    	for(int j=squareSize-1;j>=0;j--){
    		for(int i=squareSize-1;i>=0;i--){
    			if(cells[i][j].getValue()==CellHolder.EMPTY){
    				int rowNum=i-1;
    				while(rowNum>=0&&cells[rowNum][j].getValue()==CellHolder.EMPTY){
    					rowNum--;
    				}
    				if(rowNum>=0){
    					cells[i][j].shiftCellToMe(cells[rowNum][j]);
    				}else{
    					if(replaceNdx<0){
    						replaceNdx=0;
    						//logError
    						System.err.println("replaceList length doesnt match number of empty tiles!!!");
    					}
    					cells[i][j].replaceCell(replacements[replaceNdx]);
    					replaceNdx--;
    				}
    			}
    		}
    	}
    }

    private void setAdjList(CellHolder toSet,int row,int col){
        if((row-1)>=0){
            toSet.addAdjacentCellHolder(cells[row-1][col]);
        }
        if((col-1)>=0){
            toSet.addAdjacentCellHolder(cells[row][col-1]);
        }
        if((col+1)<cells[0].length){
            toSet.addAdjacentCellHolder(cells[row][col+1]);
        }
        if((row+1)<cells.length){
            toSet.addAdjacentCellHolder(cells[row+1][col]);
        }
    }
}