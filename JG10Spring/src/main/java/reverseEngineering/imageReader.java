package reverseEngineering;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;
class BfsPoint{
	public int row;
	public int col;
	private final static int UP=0;
	private final static int DOWN=1;
	private final static int LEFT=2;
	private final static int RIGHT=3;
	private final static int DONE=4;
	private int state=UP;
	private boolean[][] sharedVisitedMat=null;
	public BfsPoint(int r,int c,boolean[][] visitedMat){
		row=r;
		col=c;
		sharedVisitedMat=visitedMat;
	}
	public boolean isNextValid(int[][] grid,int nextRow,int nextCol){
		return nextRow>=0&&nextRow<grid.length&&
		       nextCol>=0&&nextCol<grid[nextRow].length&&
		       !sharedVisitedMat[nextRow][nextCol]&&
		       grid[row][col]==grid[nextRow][nextCol];
	}
	public BfsPoint next(int[][] grid){
		BfsPoint toReturn=null;
		int modR=0;
		int modC=0;
		while(state<DONE&&toReturn==null){
                 if(state==UP)   {modR=-1;modC= 0;}
            else if(state==DOWN) {modR= 1;modC= 0;}
            else if(state==LEFT) {modR= 0;modC=-1;}
            else if(state==RIGHT){modR= 0;modC= 1;}
            if(isNextValid(grid,row+modR,col+modC)){
                sharedVisitedMat[row+modR][col+modC]=true;
                toReturn=new BfsPoint(row+modR,col+modC,sharedVisitedMat);
            }
            state++;
		}
		return toReturn;
	}
	@Override
	public String toString(){
		return "(r:"+row+",c:"+col+")";
	}
}
public class imageReader {
	//public static Map<Integer,Integer> colorMap=new HashMap<>();
	public static Map<Integer,Integer> colorMap=new HashMap<Integer,Integer>(){
		{
			put(-1007536,3); put(-1028016,4); put(-6230016,1); put(-11493136,2); put(-6230016,1); 
			put(-1028016,4); put(-1007536,3); put(-11493136,2); put(-11493136,2); put(-1028016,4); 
			put(-1028016,4); put(-6230016,1); put(-6230016,1); put(-1007536,3); put(-11513616,6); 
			put(-1028016,4); put(-1007536,3); put(-6230016,1); put(-1007536,3); put(-6291456,8); 
			put(-1027936,7); put(-1007536,3); put(-16756656,5); put(-16756656,5); put(-987136,9); 
		}
	};
	//reading from vnc screen share of phone, will change based on window position and resolution
	/*
	public static final int step=85;
	public static final int startX=56+((3*step)/4);
	public static final int startY=284+((3*step)/4);
	
	//*///reading from screenshot, won't change unless I change phones
	public static final int step=185;
	public static final int startX=75+((3*step)/4);
	public static final int startY=510+((3*step)/4);
	//*/
	public static String toWebColor(Color c){
		return String.format("#%02x%02x%02x", c.getRed(),c.getGreen(),c.getBlue());
	}
	public static int loosePrecision(int rgbColor){
		int[] rgb=new int[3];
		rgb[0] = (rgbColor & 0xff0000) >> 16;//red
		rgb[1] = (rgbColor & 0x00ff00) >> 8;//green
		rgb[2] = (rgbColor & 0x0000ff) >> 0;//blue
		//int threshold=60;
		int threshold=80;
		for(int i=0;i<rgb.length;i++){
			rgb[i]=(rgb[i]/threshold)*threshold;
		}
		return new Color(rgb[0],rgb[1],rgb[2]).getRGB();
		
	}
	public static int[][] getNumbers(String filePath){
		BufferedImage img=null;
		try{
			img=ImageIO.read(new File(filePath));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return getNumbers(img);
	}
	public static int[][] getNumbers(BufferedImage img){
		
		int[][] toReturn=new int[5][];
		for(int i=0;i<5;i++){
			toReturn[i]=new int[5];
			for(int j=0;j<5;j++){
				int x=startX+step*j;
				int y=startY+step*i;
				int value=loosePrecision(img.getRGB(x, y));
				try{
					toReturn[i][j]=colorMap.get(value);
				}catch(NullPointerException ex){
					System.err.println("null ptr exception ("+i+","+j+")");
					System.err.println("at ("+x+","+y+")");
					System.err.println("value: "+value);
					System.err.println("value: "+toWebColor(new Color(value)));
					throw ex;
				}
			}
		}
		return toReturn;
	}
	public static int[] aggregate(int[][] data){
		int[] toReturn=new int[10];
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				toReturn[data[i][j]]++;
			}
		}
		return toReturn;
	}
	public static int max(int[][] data){
		int max=0;
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				if(data[i][j]>max){
					max=data[i][j];
				}
			}
		}
		return max;
	}
	public static ArrayList<BfsPoint> bfs(int[][] values,int row, int col){
		boolean[][] visiteds=new boolean[values.length][values[0].length];//booleans init to false
		visiteds[row][col]=true;
		int ndx=0;
		ArrayList<BfsPoint> visitedPoints=new ArrayList<>();
		visitedPoints.add(new BfsPoint(row,col,visiteds));
		BfsPoint next=null;
		while(ndx<visitedPoints.size()){
			while((next=visitedPoints.get(ndx).next(values))!=null){
				visitedPoints.add(next);
			};
			ndx++;
		}
		return visitedPoints;
	}
	public static String getReplacedTileList(int[][] before,int[][] after, int rowCombinedOn,int colCombinedOn){
		StringBuilder builder=new StringBuilder();
		int values[]=aggregate(before);
		builder.append("[");
		for(int i=1;i<values.length;i++){
			builder.append(i+":"+values[i]+", ");
		}
		builder.append("]  ");
		ArrayList<BfsPoint> matching=bfs(before,rowCombinedOn,colCombinedOn);
		int[] tilesPerCol=new int[5];
		for(int i=0;i<matching.size();i++){
			tilesPerCol[matching.get(i).col]++;
		}
		for(int col=0;col<after[0].length;col++){
			for(int row=0;row<after.length;row++){
				if(row<tilesPerCol[col]){
					builder.append(after[row][col]+", ");
				}
			}
		}
		return builder.toString();
	}
	private static final int[][] colorKeyValues={
		{3,4,1,2,1},
		{4,3,2,2,4},
		{4,1,1,3,6},
		{4,3,1,3,8},
		{7,3,5,5,9}
	};
	public static void initMap(Robot r,Scanner s){//pullUp colorKey screenshot
		
		Rectangle screen=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage img=r.createScreenCapture(screen);
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				r.mouseMove(startX+step*j, startY+step*i);
				/*
				int key=loosePrecision(img.getRGB(startX+step*j, startY+step*i));
				System.out.print(toWebColor(new Color(key))+":"+colorKeyValues[i][j]+" ");
				colorMap.put(key, colorKeyValues[i][j]);
				*/
				s.nextLine();
			}
			System.out.println();
		}
		System.out.println("colors initialized, press enter to continue");
		s.nextLine();
	}
	public static void __main(String[] args) throws AWTException{
		Robot r=new Robot();
		Scanner s=new Scanner(System.in);
		initMap(r,s);
		Rectangle screen=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage img=r.createScreenCapture(screen);
		int[][] grid=getNumbers(img);
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				System.out.print(grid[i][j]+", ");
			}
			System.out.println();
		}
		
	}
	public static void configureMap(){
		BufferedImage img=null;
		try{
			img=ImageIO.read(new File("C:/Users/ande5435/Desktop/key.png"));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				int x=startX+step*j;
				int y=startY+step*i;
				int value=loosePrecision(img.getRGB(x, y));
				System.out.print("put("+value+","+colorKeyValues[i][j]+"); ");
			}
			System.out.println();
		}
		int[][] data=getNumbers(img);
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				System.out.print(data[i][j]+" ");
				
			}
			System.out.println();
		}
		
	}
	public static void main(String[] args){
		
		
		String workingDir="C:/Users/ande5435/Desktop";
		String startFilePath=workingDir+"/begin.png";
		PrintWriter writer=null;
		Scanner s=null;
		try {
			writer = new PrintWriter(new FileOutputStream(new File(workingDir+"/replaceLists.txt")));
			s=new Scanner(System.in);
			int[][] before;
			int[][] after=getNumbers(startFilePath);
			String newFilePath=workingDir+"/next.png";
			int row=0;
			int col=0;
			boolean done=false;
			while(!done){
				String input=s.nextLine();
				String[] userInput=input.split(" ");
				row=Integer.parseInt(userInput[0]);
				col=Integer.parseInt(userInput[1]);
				done=Boolean.parseBoolean(userInput[2]);
				if(!done){
					before=after;
					after=getNumbers(newFilePath);
					writer.append(getReplacedTileList(before,after,row,col)+"\r\n");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(writer!=null)writer.close();
			if(s!=null)s.close();
		}
	}
}
