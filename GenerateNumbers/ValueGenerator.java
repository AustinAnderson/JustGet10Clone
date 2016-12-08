package GenerateNumbers;
import MainLogic.Grid;
public class ValueGenerator{
    private Grid theGrid;
    private GeneratorStrategy generator;
    public ValueGenerator(Grid grid){
        theGrid=grid;
        generator=new PlainGenerator();
    }
    public void update(){
        generator.updateMax(theGrid.getMaxValue());
    }

    public int next(){
        //, make random bounded by max value, observer patterns makes it update as the game goes on
        return generator.next();
    }
    public void reset(){
        generator.reset();
    }
    //{13,7,4,1,0,0,0,0,0,0,0,0,0,0,0,0} start (max 3)
    //{6,7,3,4,0,0,0,0,0,0,0,0,0,0,0,0} max 4

}
