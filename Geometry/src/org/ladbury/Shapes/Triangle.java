package org.ladbury.Shapes;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Triangle {
	
	private Point2D.Double A;
	private Point2D.Double B;
	private Point2D.Double C;
	
	//
	// Constructor (all parameters)
	//
	public Triangle(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
		// TODO: add validation, points must be different and not all in a line
		A = a;
		B = b;
		C = c;
	}
	//
	// Constructor (no parameters)
	//
	public Triangle() {
		this(	new Point2D.Double(30,30),
				new Point2D.Double(0,0),
				new Point2D.Double(50,0));
	}
		
	public Point2D.Double getA() {
		return A;
	}

	public void setA(Point2D.Double a) {
		A = a;
	}

	public Point2D.Double getB() {
		return B;
	}

	public void setB(Point2D.Double b) {
		B = b;
	}

	public Point2D.Double getC() {
		return C;
	}

	public void setC(Point2D.Double c) {
		C = c;
	}
	
	public Line2D.Double getAB() {
		return new Line2D.Double(A, B);
	}
	
	public Line2D.Double getAC() {
		return new Line2D.Double(A, C);
	}
	
	public Line2D.Double getBC() {
		return new Line2D.Double(B, C);
	}
	
	public boolean isWithinTriangle(Point2D.Double D) {
		return true;
	}
	
	public double getHeight() {
		return Math.max(Math.max(A.getY(), B.getY()),C.getY()) - Math.min(Math.min(A.getY(), B.getY()),C.getY());  
	}
	
	public double getWidth() {
		return Math.max(Math.max(A.getX(), B.getX()),C.getX()) - Math.min(Math.min(A.getX(), B.getX()),C.getX());  
	}
	public boolean isValid() {
		return (!(A.equals(B)) && !(A.equals(C)) && !(B.equals(C)));
	}
	public Rectangle2D getContainingRectangle() {
		double h = getHeight();
		double w = getWidth();
		double x = Math.min(Math.min(A.getX(), B.getX()),C.getX());
		double y = Math.min(Math.min(A.getY(), B.getY()),C.getY());
		return new Rectangle2D.Double(x,y,w,h);
	}

	public double sideLength(Line2D.Double side){
		// use Pythagoras to work out length of line
		// by constructing a right angle triangle
		// made by lines perpendicular to the axes
		double diffX = side.x2 - side.x1;
		double diffY = side.y2 - side.y1;
		return Math.abs(Math.sqrt(diffX*diffX + diffY*diffY));
	}
}
