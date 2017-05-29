package Util;
public class TerminalColorMap{
    private final static String[] Colors=new String[]{
        "\u001B[0m",
        "\u001B[40m",
        "\u001B[41m",
        "\u001B[42m",
        "\u001B[43m",
        "\u001B[44m",
        "\u001B[45m",
        "\u001B[46m",
        "\u001B[47m",
        "\u001B[48m",
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
