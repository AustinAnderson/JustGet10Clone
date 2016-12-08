package GenerateNumbers;
public class PlainGenerator extends GeneratorStrategy{
    @Override
    public int next(){
        return ((int)(Math.random()*100)%(max+1))+1;
    }
    public static void main(String[] args){
        PlainGenerator tester=new PlainGenerator();
        System.out.println("starting max is "+tester.max);
        for(int i=0;i<10;i++){
            System.out.println(tester.next());
        }
    }

}
