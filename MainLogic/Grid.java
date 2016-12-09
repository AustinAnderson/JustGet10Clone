package MainLogic;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import GenerateNumbers.ValueGenerator;
public class Grid{
    private List<CellHolder> cells;
    private int squareSize;
    private ValueGenerator valueGenerator=new ValueGenerator(this);
    public Grid(int size){
        cells=new ArrayList<>();
        squareSize=size;
        for(int i=0;i<squareSize*squareSize;i++){
            cells.add(new CellHolder(String.format("%02d",i),valueGenerator));
        }
        for(int i=0;i<squareSize*squareSize;i++){
            setAdjList(cells.get(i),i);
        }
    }
    //driver
    public static void main(String[] args){
        Grid g=new Grid(Integer.parseInt(args[0]));
        g.print();
        g.combineOn(2,2);
    }

    public void combineOn(int i,int j){
        CellHolder current=cells.get(flattenNdx(i,j));
        CellHolder next=current.nextCellHolder();
        boolean equalNeighbor=false;
        while(next!=null){
            if(current.getValue()==next.getValue()){
                equalNeighbor=true;
            }
            next=current.nextCellHolder();
        }
        if(equalNeighbor){
            bfsClear(i,j);
            current.increment();
        }
        collapse();
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
    public void update(){
        System.out.print("\u001b[2J\u001b[H");
        System.out.flush();
        print();
    }
    private void sleep(int time){
        try{
            Thread.sleep(time);
        }catch(InterruptedException e){};
    }

    public void bfsClear(int i, int j){
        Queue<CellHolder> bfsQ=new LinkedList<>();
        CellHolder dontChange=cells.get(flattenNdx(i,j));
        bfsQ.add(dontChange);
        CellHolder topNeighbor=null;
        resetAll();

        while(bfsQ.peek()!=null){
            if(topNeighbor!=null&&bfsQ.peek().getValue()==topNeighbor.getValue()){
                if(!topNeighbor.equals(dontChange)&&topNeighbor.getValue()!=0){
                    bfsQ.add(topNeighbor);
                }
            }
            topNeighbor=bfsQ.peek().nextCellHolder();
            if(topNeighbor==null){
                CellHolder removed=bfsQ.remove();
                if(!removed.equals(dontChange)){
                    removed.clear();
                    update();
                    sleep(100);
                }
            }
        }
    }

    public void collapse(){
        valueGenerator.update();
        for(int k=0;k<squareSize;k++){
            for(int j=0;j<squareSize;j++){
                cells.get(flattenNdx(0,j)).fillNullCell();
            }
            for(int i=squareSize-1;i>0;i--){
                for(int j=0;j<squareSize;j++){
                    if(cells.get(flattenNdx(i,j)).getValue()==0){
                        cells.get(flattenNdx(i,j)).shiftCellToMe(
                            cells.get(flattenNdx(i-1,j))
                        );
                    }
                }
            }
            update();
            sleep(200);
        }
    }
    public int getMaxValue(){
        int maxValue=0;
        for(int i=0;i<squareSize;i++){
            for(int j=0;j<squareSize;j++){
                int currentValue=cells.get(flattenNdx(i,j)).getValue();
                if(currentValue>maxValue){
                    maxValue=currentValue;
                }

            }
        }
        return maxValue;

    }
    public void print(){
        for(int i=0;i<squareSize;i++){
            for(int j=0;j<squareSize;j++){
                System.out.print(cells.get(flattenNdx(i,j)).print()+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public String toDebugString(){
        StringBuilder builder=new StringBuilder();
        builder.append("<");
        builder.append(cells.get(0).toDebugString());
        for(int i=1;i<squareSize*squareSize;i++){
            builder.append(", ");
            builder.append(cells.get(i).toDebugString());
        }
        builder.append(">");
        return builder.toString();
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
    private void resetAll(){
        for(CellHolder currentHolder:cells){
            currentHolder.resetIterator();
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
