import java.util.List;
import java.util.ArrayList;
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
        System.out.println(g.toDebugString());
    }

    public void collapse(){
        valueGenerator.update();
        //...
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
                System.out.print(cells.get(flattenNdx(i,j))+" ");
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
