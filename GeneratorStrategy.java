public abstract class GeneratorStrategy{
    final public static int INITIAL_MAX=2;
    protected int max=INITIAL_MAX;
    public void reset(){
        max=INITIAL_MAX;
    }
    public void updateMax(int newMax){
        if(newMax>max){
            max=newMax;
        }
    }
    //to be overriden
    public abstract int next();
}
