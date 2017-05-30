package MainLogic;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Deque;
import java.util.ArrayDeque;
import GenerateNumbers.ValueGenerator;
import Util.TerminalColorMap;
public class Grid{
    private List<CellHolder> cells;
    private boolean terminalDebug=false;
    private int squareSize;
    private ValueGenerator valueGenerator=new ValueGenerator(this);
    public Grid(int size){
        cells=new ArrayList<>();
        squareSize=size;
        for(int i=0;i<squareSize*squareSize;i++){
            cells.add(new CellHolder(expandNdx(i),valueGenerator));
        }
        for(int i=0;i<squareSize*squareSize;i++){
            setAdjList(cells.get(i),i);
        }
    }
    public void setDebug(){
        terminalDebug=true;
    }
    //driver
    public static void main(String[] args){
        Grid g=new Grid(Integer.parseInt(args[0]));
        g.setDebug();
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
        if(terminalDebug){
            //System.out.print("\u001b[2J\u001b[H");
            System.out.print("\u001b[H");
            System.out.flush();
            print();
        }
    }
    public void sleep(int time){
        if(terminalDebug){
            try{
                Thread.sleep(time);
            }catch(InterruptedException e){};
        }
    }

    public void bfsClear(int i, int j){
        Queue<Transition> bfsQ=new LinkedList<>();
        Deque<Transition> animate=new ArrayDeque<>();
        TransitionList tList=new TransitionList();
        CellHolder dontChange=cells.get(flattenNdx(i,j));
        bfsQ.add(new Transition(dontChange,dontChange));
        CellHolder topNeighbor=null;
        resetAll();
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
        tList.animate(this);
        System.out.print("\u001b[2J\u001b[H");
        print();
        System.out.println(tList.print());
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
                System.out.print(TerminalColorMap.clear());
                System.out.print(cells.get(flattenNdx(i,j)).print()+" ");
            }
            System.out.println(TerminalColorMap.clear()+" "+i);
        }
        System.out.println();
        for(int i=0;i<squareSize;i++){
            if(i/10!=0){
                System.out.print(i/10);
            }else{
                System.out.print(" ");
            }
            System.out.print(" ");
        }
        System.out.println();
        for(int i=0;i<squareSize;i++){
            System.out.print(i%10+" ");
        }
        System.out.println();
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
    private String expandNdx(int flattened){
        return "{ 'x':"+(flattened/squareSize)+", 'y':"+(flattened%squareSize)+"}";
    }

}
