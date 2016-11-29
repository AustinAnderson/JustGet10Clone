import GeneratorStrategy;
public class PlainGenerator extends GeneratorStrategy{
    @Override
    public int next(){
        return Math.Random()%max;
    }
}
