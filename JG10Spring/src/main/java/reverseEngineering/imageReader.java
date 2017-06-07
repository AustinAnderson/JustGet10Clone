package reverseEngineering;

import java.awt.Color;
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
	public static final int step=185;
	public static final int startX=75+((3*step)/4);
	public static final int startY=510+((3*step)/4);
	public static String toWebColor(Color c){
		return String.format("#%02x%02x%02x", c.getRed(),c.getGreen(),c.getBlue());
	}
	public static int loosePrecision(int rgbColor){
		int[] rgb=new int[3];
		rgb[0] = (rgbColor & 0xff0000) >> 16;//red
		rgb[1] = (rgbColor & 0x00ff00) >> 8;//green
		rgb[2] = (rgbColor & 0x0000ff) >> 0;//blue
		int threshold=80;
		for(int i=0;i<rgb.length;i++){
			rgb[i]=(rgb[i]/threshold)*threshold;
		}
		return new Color(rgb[0],rgb[1],rgb[2]).getRGB();
		
	}

	public static Map<Integer,Integer> colorMap=new HashMap<Integer,Integer>(){
		private static final long serialVersionUID = 1L;
		{
			put(-6230016,1);  
			put(-11493136,2);  
			put(-1007536,3);  
			put(-1028016,4);  
			put(-16756656,5);  
			put(-11513616,6);  
			put(-1027936,7);  
			put(-6291456,8);  
			put(-987136,9); 

		}
	};
	public static int[][] getNumbers(String filePath){
		
		BufferedImage img=null;
		try{
			img=ImageIO.read(new File(filePath));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		int[][] toReturn=new int[5][];
		for(int i=0;i<5;i++){
			toReturn[i]=new int[5];
			for(int j=0;j<5;j++){
				int value=loosePrecision(img.getRGB(startX+step*j, startY+step*i));
				toReturn[i][j]=colorMap.get(value);
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
		int max=max(before);
		ArrayList<BfsPoint> matching=bfs(before,rowCombinedOn,colCombinedOn);
		int[] tilesPerCol=new int[5];
		for(int i=0;i<matching.size();i++){
			tilesPerCol[matching.get(i).col]++;
		}
		builder.append(max+": ");
		for(int col=0;col<after[0].length;col++){
			for(int row=0;row<after.length;row++){
				if(row<tilesPerCol[col]){
					builder.append(after[row][col]+", ");
				}
			}
		}
		return builder.toString();
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
