package MainLogic;
import Util.TerminalColorMap;
public class Cell{
    private int value=0;
    private String name;
    public Cell(String name, int value){
        this.name=name;
        this.value=value;
    }
    public void increment(){
        value++;
    }
    public int getValue(){
        return value;
    }
    @Override
    public String toString(){
        return TerminalColorMap.get(value)+value;
    }
}
