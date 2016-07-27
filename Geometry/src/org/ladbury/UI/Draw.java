package org.ladbury.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import org.ladbury.Shapes.Triangle;
import org.ladbury.Triangulation.Triangulation;


public class Draw extends JComponent {

	/**
	 * 
	 */
	private static final long 	serialVersionUID = 3229574199128951198L;
	public static final int 	LABLE_X_OFFSET = 1;
	public static final int 	LABLE_Y_OFFSET = 1;
	public static final int		DIAGRAM_X_OFFSET = 10;
	public static final int		DIAGRAM_Y_OFFSET = 10;
	public static final int		CHAR_WIDTH = 1;
	public static final int		CHAR_HEIGHT = 1;
	public static final int 	LABLE_WIDTH = 16*CHAR_WIDTH;
	public static final double	PI = 3.14159;
	
	private  int 		scale = 15;
	private  double 	dotSize = 0.5;
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(DIAGRAM_X_OFFSET,DIAGRAM_Y_OFFSET);
		g2.scale(scale,scale);
		//g2.rotate(PI); //PI radians = 180 Degrees
		g2.setStroke(new BasicStroke(1.0f/scale));
		g2.setFont(new Font("SanSerif", Font.PLAIN, CHAR_WIDTH));
		
		drawTriangle(g2,Triangulation.getTriangle(),Color.BLUE);
		drawPoint(g2,Triangulation.getD(),Color.BLUE);
		drawPoint(g2,Triangulation.getTriangle().getA(),Color.BLACK);
		drawPoint(g2,Triangulation.getTriangle().getB(),Color.BLACK);
		drawPoint(g2,Triangulation.getTriangle().getC(),Color.BLACK);
		drawPointLable(g2,Triangulation.getTriangle().getA(),Color.RED,"A");
		drawPointLable(g2,Triangulation.getTriangle().getB(),Color.RED,"B");
		drawPointLable(g2,Triangulation.getTriangle().getC(),Color.RED,"C");
		drawPointLable(g2,Triangulation.getD(),Color.RED,"D");
		drawLine(g2,new Line2D.Double(Triangulation.getTriangle().getB(),Triangulation.getD()),Color.YELLOW);
		drawLine(g2,new Line2D.Double(Triangulation.getTriangle().getC(),Triangulation.getD()),Color.YELLOW);
	}
	public void drawPointLable(Graphics2D g2, Point2D.Double p, Color c, String l){
		String textX = String.format("%,.3f", p.getX());
		String textY = String.format("%,.3f", p.getY());
		g2.setPaint(c);
		g2.drawString(l+" ["+textX+","+textY+"]", (float)p.getX()+LABLE_X_OFFSET, (float)p.getY()+LABLE_Y_OFFSET);
	}
	public void drawTriangle(Graphics2D g2, Triangle t, Color c){
		g2.setPaint(c);
		g2.draw(t.getAB());
		g2.draw(t.getAC());
		g2.draw(t.getBC());
	}
	public void drawPoint(Graphics2D g2, Point2D.Double p, Color c){
		g2.setPaint(c);
		Ellipse2D dot = new Ellipse2D.Double(p.getX()-dotSize/2, p.getY()-dotSize/2, dotSize, dotSize);
		g2.draw(dot);
	}

	public void drawLine(Graphics2D g2, Line2D.Double l, Color c){
		g2.setPaint(c);
		g2.draw(l);
	}
	public int getScale() {	
		return scale;
	}
	public void setScale(Dimension framesize, Rectangle2D r){
		double xScale = 0;
		double yScale = 0;
		xScale = framesize.width/(r.getWidth()+DIAGRAM_X_OFFSET+LABLE_X_OFFSET+LABLE_WIDTH);
		yScale = framesize.height/(r.getHeight()+DIAGRAM_Y_OFFSET+LABLE_Y_OFFSET+CHAR_HEIGHT);
		System.out.println("Frame:["+framesize.width+","+framesize.height+
							" Diagram: ["+r.getWidth()+","+r.getHeight()+"]"+
							" Padding: ["+(DIAGRAM_X_OFFSET+LABLE_X_OFFSET+LABLE_WIDTH)+","+(DIAGRAM_Y_OFFSET+LABLE_Y_OFFSET+CHAR_HEIGHT)+"]");
		scale = (int)Math.round(Math.min(xScale, yScale)+0.5);
		System.out.println("Scale: "+scale+"["+xScale+","+yScale+"]");
	}
//	public Dimension getPreferredSize(){
//		return new Dimension(1000,800);
//	}
}
