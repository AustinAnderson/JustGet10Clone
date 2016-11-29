public class ValueGenerator{
    private Grid theGrid;
    private int maxValue=3;
    public ValueGenerator(Grid grid){
        theGrid=grid;
    }
    public void update(){
        maxValue=theGrid.getMaxValue();
    }

    public int next(){
        //what probabilities to use?
        //stub, make random bounded by max value, observer patterns makes it update as the game goes on
        
        return 0;
    }
    //{13,7,4,1,0,0,0,0,0,0,0,0,0,0,0,0} start (max 3)
    //{6,7,3,4,0,0,0,0,0,0,0,0,0,0,0,0} max 4

}
