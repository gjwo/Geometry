package org.ladbury.Shapes;

import java.awt.geom.Line2D;
import java.io.InvalidObjectException;

import javax.naming.InvalidNameException;

public class LabelledLine {
	private LabelledPoint[] points = new LabelledPoint[2];

	private int getIndex(String n)throws InvalidNameException{
		int i;
		for (i = 0; i<=1; i++){
			if (points[i].isNamed(n)) return i;
		}
		throw new InvalidNameException();
	}

	public LabelledLine(LabelledPoint p1,LabelledPoint p2) throws InvalidObjectException, InvalidNameException{
		validate(p1,p2); //exit via exception if invalid
		points[0] = p1;
		points[1] = p2;
	}
	private void validate(LabelledPoint p1,LabelledPoint p2) throws InvalidObjectException, InvalidNameException{
		if(p1.isNamed(p2.name())) 
			throw new InvalidNameException();
		if((p1.getPoint().distance(p2.getPoint()) == 0)) 
			throw new InvalidObjectException("Co-located points"); // the two points are co-located
	}
	public void setEndLocation(LabelledPoint p1)throws InvalidObjectException, InvalidNameException{
		for (int i = 0; i<=2; i++){
			if(points[i].isNamed(p1.name())){
				validate(p1,points[(i+1)%2]); //exit via exception if invalid
				points[i] = p1;
				return;
			}
		}
		throw new InvalidNameException();
	}
	public void setEndName(String n, String newName)throws InvalidObjectException,InvalidNameException{
	int  i;	
		i = getIndex(n);
		LabelledPoint newp = new LabelledPoint(points[i].getX(),points[i].getY(),newName);
		validate(newp ,points[(i+1)%2]); //exit via exception if invalid			
		points[i] = newp;
	}
	
	public double length(){
		return points[0].distance(points[1]);
	}

	public Line2D.Double getLine(String n1,String n2)throws InvalidNameException{
		//Generates a line between the two named points
		int i1,i2;
		
		i1 = getIndex(n1);
		i2 = getIndex(n2);
		return new Line2D.Double(points[i1],points[i2]);
	}
	public Line2D.Double getLine(){
		//Generates a line between the two named points
		return new Line2D.Double(points[0],points[1]);
	}

}
