package com.andersonau.implementationLogic.MainLogic;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties({"value"})
public class CellHolder{
    private List<CellHolder> adjList=new ArrayList<>();
    private int next=0;
    private Cell cell=null;
    private boolean visited=false;
    private final int row;
    private final int col;

    public CellHolder(int y,int x,int initialValue){
    	this.row=y;
    	this.col=x;
        cell=new Cell(initialValue);
    }

    public boolean canCombine(){
        boolean equalNeighbor=false;
        
        CellHolder next=nextCellHolder();
        while(next!=null){
            if(next.getValue()==getValue()){
                equalNeighbor=true;
            }
            next=nextCellHolder();
        }
        return equalNeighbor;
    }

    public int getCol(){
    	return col;
    }
    public int getRow(){
    	return row;
    }

    public int getValue(){
        return cell.getValue();
    }
    public void addAdjacentCellHolder(CellHolder toAdd){
        adjList.add(toAdd);
    }
    public void setVisited(){
        visited=true;
    }
    public boolean beenVisited(){
        return visited;
    }
    //returns null if no cell is next, otherwise returns the
    //next adjacent cell
    public CellHolder nextCellHolder(){
        CellHolder toReturn=null;
        if(next<adjList.size()){
            toReturn=adjList.get(next);
            next++;
        }
        return toReturn;
    }
    @Override
    public String toString(){
    	return "(r:"+row+",c:"+col+"): "+getValue();
    }
}
/*
if((x-n)>=0){
    add x-n to xlist
}
if(x%n!=0){
    add x-1 to xlist
}
if((x+1)%n!=0){
    add x+1 to xlist
}
if((x+n)<(n*n){
    add x+n to xlist
}
   0 1 2 3 4 5 6 7 8
0    0   0          
1  0   0   0        
2    0       0      
3  0       0   0    
4    0   0   0   0
5      0   0       0
6        0       0 
7          0   0   0
8            0   0
0 1 2
3 4 5
6 7 8

each cell stores it's 9djList
grid is list of cells, when printing just put newline after n cells where n is side length
   0 1 2 3 4 5 6 7 8 9 A B C D E F 
0    1     4               
1  0   2     5             
2    1   3     6           
3      2         7         
4  0         5     8       
5    1     4   6     9     
6      2     5   7     A   
7        3     6         B 
8          4         9     C
9            5     8   A     D
A              6     9   B     E
B                7     A         F
C                  8         D
D                    9     C   E
E                      A     D   F
F                        B     E  
0 1 2 3                    
4 5 6 7
8 9 A B
C D E F
*/
