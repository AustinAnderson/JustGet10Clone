import java.util.Scanner;
import MainLogic.Grid;
public class TerminalMain{
    public static void main(String[] args){
        Grid g=new Grid(Integer.parseInt(args[0]));
        g.setDebug();
        Scanner scanner=new Scanner(System.in);
        String line=null;
        System.out.print("\u001b[2J\u001b[H");
        g.print();
        while(!"q".equals(line)){
            line=scanner.nextLine();
            int x=0;
            int y=0;
            String[] coord=line.split(" ");
            x=Integer.parseInt(coord[0]);
            y=Integer.parseInt(coord[1]);
            g.combineOn(x,y);

        }

    }
}
