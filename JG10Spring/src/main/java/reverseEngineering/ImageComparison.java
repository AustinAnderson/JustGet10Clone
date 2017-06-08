package reverseEngineering;

import java.awt.Color;

public class ImageComparison {

	private static final double[][] conversionMat={
		{.49000,.31000,.20000},
		{.17697,.81240,.01063},
		{.00000,.01000,.99000}
	};
	private static final double[] xyzWhitePoint={95.047,100,108.883};
	private static final double Ka=(175/198.04)*(xyzWhitePoint[0]+xyzWhitePoint[1]);
	private static final double Kb=(70/218.11)*(xyzWhitePoint[1]+xyzWhitePoint[2]);
	private double[] toXYZ(Color in){
		double r=in.getRed();
		double g=in.getGreen();
		double b=in.getBlue();
		double[] xyzColor=new double[3];
		xyzColor[0]=(conversionMat[0][0]*r+conversionMat[0][1]*g+conversionMat[0][2]*b)/conversionMat[1][0];
		xyzColor[1]=(conversionMat[1][0]*r+conversionMat[1][1]*g+conversionMat[1][2]*b)/conversionMat[1][0];
		xyzColor[2]=(conversionMat[2][0]*r+conversionMat[2][1]*g+conversionMat[2][2]*b)/conversionMat[1][0];
		return xyzColor;
	}
	private double[] toHLab(double[] xyz){
		double YoverYwhite=xyz[1]/xyzWhitePoint[1];
		double sqrtYoverYwhite=Math.sqrt(YoverYwhite);
		double[] hLab=new double[3];
		hLab[0]=100*sqrtYoverYwhite;
		hLab[1]=Ka*(((xyz[0]/xyzWhitePoint[0])-YoverYwhite)/sqrtYoverYwhite);
		hLab[2]=Kb*((YoverYwhite-(xyz[2]/xyzWhitePoint[2]))/sqrtYoverYwhite);
		return hLab;
		
	}
	private double compare(double[] hLab1,double[] hLab2){
		return ((hLab2[0]-hLab1[0])*(hLab2[0]-hLab1[0]))+
			   ((hLab2[1]-hLab1[1])*(hLab2[1]-hLab1[1]))+
			   ((hLab2[2]-hLab1[2])*(hLab2[2]-hLab1[2]));
	}
	public double compare(Color c1,Color c2){
		return compare(toHLab(toXYZ(c1)),toHLab(toXYZ(c2)));
	}
}
