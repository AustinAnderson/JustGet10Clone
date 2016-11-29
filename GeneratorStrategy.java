public abstract class GeneratorStrategy{
    protected static int max=0;
    public void reset(){
        max=0;
    }
    public void updateMax(int newMax){
        if(newMax>GeneratorStrategy.max){
            GeneratorStrategy.max=newMax;
        }
    }
    //to be overriden
    public abstract int next();
}
