package org.ladbury.Shapes;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.InvalidObjectException;
import javax.naming.InvalidNameException;

public class LabelledTriangle {
private LabelledPoint[] points = new LabelledPoint[3];

private int getIndex(String n)throws InvalidNameException{
	int i;
	for (i = 0; i<=2; i++){
		if (points[i].isNamed(n)) return i;
	}
	throw new InvalidNameException();
}

public LabelledTriangle(LabelledPoint p1,LabelledPoint p2,LabelledPoint p3) throws InvalidObjectException, InvalidNameException{
	validate(p1,p2,p3); //exit via exception if invalid
	points[0] = p1;
	points[1] = p2;
	points[3] = p3;
}
private void validate(LabelledPoint p1,LabelledPoint p2,LabelledPoint p3) throws InvalidObjectException, InvalidNameException{
	if(p1.isNamed(p2.name())||p2.isNamed(p3.name())||p3.isNamed(p1.name())) 
		throw new InvalidNameException();
	if(Math.abs((p1.getX()*(p2.getY()-p3.getY()) + p2.getX()*(p3.getY()-p1.getY()) + p3.getX()*(p1.getY()-p2.getY()) / 2))<=0.01)
		throw new InvalidObjectException("All points on a line"); // points on a line
	if((p1.getPoint().distance(p2.getPoint()) == 0)||(p2.getPoint().distance(p3.getPoint()) == 0)||(p1.getPoint().distance(p3.getPoint()) == 0)) 
		throw new InvalidObjectException("Co-located points"); // two or more points co-located
}
public void setApexLocation(LabelledPoint p1)throws InvalidObjectException, InvalidNameException{
	int i;
	i = getIndex(p1.name());//exit via exception if invalid
	validate(p1,points[(i+1)%3], points[(i+2)%3]); //exit via exception if invalid
	points[i] = p1;
}
public void setApexName(String n, String newName)throws InvalidObjectException,InvalidNameException{
	int i;
	i = getIndex(n);//exit via exception if invalid
	LabelledPoint newp = new LabelledPoint(points[i].getX(),points[i].getY(),newName);
	validate(newp ,points[(i+1)%3], points[(i+2)%3]); //exit via exception if invalid			
	points[i] = newp;
}

public boolean isWithinTriangle(Point2D.Double D) {
	return true;
}
public boolean contains(Point2D.Double test) {
    int i;
    int j;
    boolean result = false;
    for (i = 0, j = points.length - 1; i < points.length; j = i++) {
      if ((points[i].y > test.y) != (points[j].y > test.y) &&
          (test.x < (points[j].x - points[i].x) * (test.y - points[i].y) / (points[j].y-points[i].y) + points[i].x)) {
        result = !result;
       }
    }
    return result;
}

public double getHeight() {
	return Math.max(Math.max(points[0].getY(), points[1].getY()),points[2].getY()) - Math.min(Math.min(points[0].getY(), points[1].getY()),points[2].getY());  
}

public double getWidth() {
	return Math.max(Math.max(points[0].getX(), points[1].getX()),points[2].getX()) - Math.min(Math.min(points[0].getX(), points[1].getX()),points[2].getX());  
}
public boolean isValid() {
	return (!(points[0].equals(points[1])) && !(points[0].equals(points[2])) && !(points[1].equals(points[2])));
}
public Rectangle2D getContainingRectangle() {
	double h = getHeight();
	double w = getWidth();
	double x = Math.min(Math.min(points[0].getX(), points[1].getX()),points[2].getX());
	double y = Math.min(Math.min(points[0].getY(), points[1].getY()),points[2].getY());
	return new Rectangle2D.Double(x,y,w,h);
}

public double sideLength(String n1,String n2)throws InvalidNameException{
	int i1,i2;
	
	i1 = getIndex(n1);
	i2 = getIndex(n2);
	return points[i1].distance(points[i2]);
}

public Line2D.Double getSide(String n1,String n2)throws InvalidNameException{
	//Generates a line between the two named points
	int i1,i2;
	
	i1 = getIndex(n1);
	i2 = getIndex(n2);
	return new Line2D.Double(points[i1],points[i2]);
}
public double angle(String n1)throws InvalidNameException{
	//gives the angle at the named Apex
	int i;
	Double a,b,c;
	
	i = getIndex(n1);
	a = sideLength(points[(i+1)%3].name(),points[(i+2)%3].name());
	b = sideLength(points[(i)%3].name(),points[(i+1)%3].name());
	c = sideLength(points[(i)%3].name(),points[(i+2)%3].name());
	
	return Math.acos((b*b+c*c-a*a)/2*b*c);
}
}
