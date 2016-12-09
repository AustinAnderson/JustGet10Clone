package MainLogic;
import java.util.List;
import java.util.ArrayList;
import GenerateNumbers.ValueGenerator;
public class CellHolder{
    private List<CellHolder> adjList=new ArrayList<>();
    private int next=0;
    private Cell cell=null;
    private ValueGenerator valueGenerator;
    private String name;
    private int replaceCount=0;

    public CellHolder(String id,ValueGenerator valueGenerator){
        name=id;
        this.valueGenerator=valueGenerator;
        cell=new Cell(name+replaceCount,valueGenerator.next());
    }

    public void clear(){
        next=0;
        cell=null;
    }

    public void shiftCellToMe(CellHolder other){
        this.next=0;
        other.next=0;
        this.cell=other.cell;
        other.cell=null;
    }
    public boolean fillNullCell(){
        boolean toReturn=false;
        if(cell==null){
            replaceCount++;
            toReturn=true;
            cell=new Cell(name+replaceCount,valueGenerator.next());
        }
        return toReturn;
    }
    public boolean canCombine(){//need to test this
        boolean equalNeighbor=false;
        
        CellHolder next=nextCellHolder();
        while(next!=null){
            if(next.getValue()==getValue()){
                equalNeighbor=true;
            }
            next=nextCellHolder();
        }
        resetIterator();
        return equalNeighbor;
    }


    @Override
    public String toString(){
        return name;
    }
    public String print(){
        String toReturn=" ";
        if(cell!=null){
            toReturn=cell.toString();
        }
        return toReturn;
    }

    public String toDebugString(){
        StringBuilder builder=new StringBuilder();
        builder.append("[");
        builder.append(getName());
        if(cell!=null){
            builder.append("("+cell.toDebugString()+")");
        }else{
            builder.append("(null)");
        }
        builder.append(", {");
        CellHolder next=nextCellHolder();
        if(next!=null){
            builder.append(next.getName());
            next=nextCellHolder();
        }
        while(next!=null){
            builder.append(", ");
            builder.append(next.getName());
            next=nextCellHolder();
        }
        builder.append("} ]");
        resetIterator();
        return builder.toString();
    }

    public int getValue(){
        int toReturn=0;
        if(cell!=null){
            toReturn=cell.getValue();
        }
        return toReturn;
    }

    public void increment(){
        cell.increment();
    }


    public void addAdjacentCellHolder(CellHolder toAdd){
        adjList.add(toAdd);
    }
    public void resetIterator(){
        next=0;
    }
    public String getName(){
        return name;
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
