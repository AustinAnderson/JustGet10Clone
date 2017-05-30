package Util;
public class TerminalColorMap{
    private final static String[] Colors=new String[]{
        "\u001B[0m", 
        //1
        "\u001B[42;30m",//green
        //2
        "\u001B[02;44;34m",//blue
        //3
        "\u001B[02;43;35m",//yellow
        //4
        "\u001B[41m",//red
        //5
        "\u001B[01;40m",//black
        //6
        "\u001B[01;45m",//
        //7
        "\u001B[01;46m",//
        //8
        "\u001B[01;47m",//
        //9
        "\u001B[01;48m",//9
    };
    public static String get(int i){
        String toReturn=Colors[0];
        if(i<Colors.length){
            toReturn=Colors[i];
        }
        return toReturn;
    }
    public static String clear(){
        return Colors[0];
    }
}
