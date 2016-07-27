package org.ladbury.Triangulation;

import java.awt.EventQueue;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import org.ladbury.Shapes.Triangle;
import org.ladbury.UI.PlotFrame;
import org.ladbury.UI.UIFrame;

// Main class (entry point) and calculation of unknown point pointD
public class Triangulation {

	private static Triangle triangle = new Triangle(); // creates the triangle
	private static Point2D.Double pointD = new Point2D.Double(0,0);
	private static PlotFrame plotFrame = new PlotFrame();
	private static double lengthBD = 37;
	private static double lengthCD = 20;

	public static void main(String[] args) { // start point
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				UIFrame frame = new UIFrame(); // opens UI
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the close action
				frame.setVisible(true); //makes it visible
				//System.out.println("Dx = " + pointD.getX() + " Dy = " + pointD.getY());
			}
		});
	}

	public static void triangulate1(Point2D.Double paramPointB,Point2D.Double paramPointC, double distanceBD, double distanceCD) {
		// defines the method and requests the points B & C and lengths BD and CD.
		// to work point B is at the origin and point C on the X-axis
		// so for a general solution we need the ability to translate the triangle so B is at the Origin
		// and rotate around B so that C is on the X-Axis
		lengthBD = distanceBD;
		lengthCD = distanceCD;
		Point2D rebasedC = new Point2D.Double(); 
		double x;
		double y;
		// rebase coordinate system for C as if point B was the Origin (0,0)
		rebasedC.setLocation(paramPointC.getX() - paramPointB.getX(), paramPointC.getY() - paramPointB.getY()); 
		
		// calculate coordinates of point D
		x = 0.5*(rebasedC.getX() + ( (distanceBD*distanceBD) / rebasedC.getX() ) - ( (distanceCD*distanceCD) / rebasedC.getX() ) ); // the triangulation formula.
		y = Math.sqrt(Math.abs(((distanceBD*distanceBD)-(x*x)))); // derives the Y from the X using Pythagoras
		
		// Translate point pointD back to the original coordinate system
		x = x+paramPointB.getX();
		y = y+paramPointB.getY();
		pointD.setLocation(x,y); 
		
		
	}

	public static void triangulate(Point2D.Double paramPointB,Point2D.Double paramPointC, double distanceBD, double distanceCD) {
		// Sets unknown point D using the points B & C and lengths BD and CD.
		// Constraints
		// to work point B is at the origin and point C on the X-axis
		// so for a general solution we need the ability to translate the triangle so B is at the Origin
		// and rotate around B so that C is on the X-Axis
		// this version assumes the above conditions are met by B & C so no coordinate transformations are required
		double lengthBC;
		lengthBD = distanceBD;
		lengthCD = distanceCD;
		lengthBC = triangle.sideLength(new Line2D.Double(paramPointB, paramPointC));
		pointD.setLocation(	radicalChordIntersectDistance(distanceBD, distanceCD, lengthBC),
							halfIntersectingChordLength(distanceBD, distanceCD, lengthBC));
	}
	public static double radicalChordIntersectDistance(double r1, double r2, double d){
		// r1 radius of circle centred at [0,0]
		// r2 radius of circle centred at [d,0]
		// d distance between circles
		// returns the distance from the centre of circle r1 (x) to the point where the line
		// between the two intersect points (d) of the circles crosses the line between the two centres
		return (d*d-r2*r2+r1*r1)/(2*d);
	}
	public static double halfIntersectingChordLength(double r1, double r2, double d){
		// r1 radius of circle centred at [0,0]
		// r2 radius of circle centred at [d,0]
		// d distance between circles
		// returns half the distance between the two circles intersection points (y)
	
		return Math.sqrt(Math.abs((-d+r2-r1)*(-d-r2+r1)*(-d+r2+r1)*(d+r2+r1)))/(2*d);
	}
	
	//getters & setters	
	public static Triangle getTriangle() {
		return triangle;
	}
	public static void setTriangle(Triangle triangle) {
		Triangulation.triangle = triangle;
	}
	public static PlotFrame getPlotFrame() {
		return plotFrame;
	}
	public static void setPlotFrame(PlotFrame plotFrame) {
		Triangulation.plotFrame = plotFrame;
	}
	public static double getLengthBD() {
		return lengthBD;
	}
	public static void setLengthBD(double lengthBD) {
		Triangulation.lengthBD = lengthBD;
	}
	public static double getLengthCD() {
		return lengthCD;
	}
	public static void setLengthCD(double lengthCD) {
		Triangulation.lengthCD = lengthCD;
	}
	public  static Point2D.Double getD(){
		return pointD;
	}
	public static void setD(Point2D.Double pointD) {
		Triangulation.pointD = pointD;
	}
}
