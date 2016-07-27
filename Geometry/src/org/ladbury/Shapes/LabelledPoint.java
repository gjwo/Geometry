package org.ladbury.Shapes;

import java.awt.geom.Point2D;

public class LabelledPoint extends Point2D.Double {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3406748742757211376L;
	private String name; //single character only
	
	public LabelledPoint(double x,double y, String n){
		name = new String(n);
		super.setLocation(x,y);
	}
	public LabelledPoint(Point2D.Double p, String n){
		name = new String(n);
		super.setLocation(p);
	}
	public LabelledPoint(){
		name = new String("p");
		super.setLocation(0,0);
	}
	public String name() {
		return name;
	}
	public void setName(char c) {
		name = new String(""+c);
	}
	public void setName(String n) {
		name = new String(n.substring(0,0));
	}
	public boolean isNamed(String n){
		return name.equalsIgnoreCase(n.trim());
	}
	public String getLable(){
		return new String(name+" ["+getX()+","+getY()+"]");
	}
	public Point2D getPoint(){
		return new Point2D.Double(getX(),getY());
	}
}
