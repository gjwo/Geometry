package org.ladbury.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import org.ladbury.Triangulation.Triangulation;


public class PlotFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -907717212070546259L;
	private Dimension screenSize;
	
	private Draw diagram = new Draw();

	public PlotFrame() {
		// default size based on screen
		setResizable(true);
		setTitle("Visualisation");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width/2,screenSize.height/2);
		setMaximumSize(screenSize);
		setMinimumSize(getSize());
		addComponentListener(new ComponentListener() 
		{  
				@Override
				public void componentHidden(ComponentEvent arg0) {
				}
				@Override
				public void componentMoved(ComponentEvent arg0) {
				}
				@Override
				public void componentResized(ComponentEvent arg0) {
					Component c = (Component)arg0.getSource();
		            ((PlotFrame)c).plot(); //causes re-scaling
				}
				@Override
				public void componentShown(ComponentEvent arg0) {
				}
		});
		//set origin for plot on screen
		setLocation(((screenSize.width/2) - getSize().width /2),((screenSize.height/2) - getSize().height/2));
		diagram.setScale(getSize(), Triangulation.getTriangle().getContainingRectangle());
		add(diagram);
		pack();
	}
	
	public void plot(){
		diagram.setScale(getSize(), Triangulation.getTriangle().getContainingRectangle());
	}

	// Getters & setters

	public  Draw getDiagram() {
		return diagram;
	}

	public void setDiagram(Draw d) {
		diagram = d;
	}
}
