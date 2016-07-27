package org.ladbury.Shapes;

import java.awt.geom.Point2D;

public class Arc {
	
	private Point2D centre;
	private double radius;
	
	public Arc() {
		
	}
	
	public Arc(Point2D c, double r) {
		
		this.centre = c;
		radius = r;
		
	}
	public void setCentre(Point2D c) {
		this.centre = c;
	}
	public void setRadius(double r) {
		this.radius = r;
	}
	public Point2D getCentre() {
		return centre;
	}
	public double getRadius() {
		return radius;
	}

}
