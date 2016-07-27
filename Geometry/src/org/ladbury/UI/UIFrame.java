package org.ladbury.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.ladbury.Triangulation.Triangulation;


public class UIFrame extends JFrame {
	/**
	 * Definition of user interface outer frame and
	 * input handlers
	 */
	private static final long serialVersionUID = -2468108405604165125L;
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 160;
	private JFormattedTextField AX;
	private JFormattedTextField BX;
	private JFormattedTextField CX;
	private JFormattedTextField AY;
	private JFormattedTextField BY;
	private JFormattedTextField CY;
	private JFormattedTextField AD;
	private JFormattedTextField BD;
	private JFormattedTextField CD;
	private JFormattedTextField DX;
	private JFormattedTextField DY;
	private JFormattedTextField DD;
	private JButton triangulate;
	private JButton show;
	
	// Listener for text fields
	private DocumentListener fieldListener = new DocumentListener()
	{

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			setField(arg0);	
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			setField(arg0);	
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			setField(arg0);	
		}	
	};
	
	private void setField(DocumentEvent evt){
		// Update any non empty, changeable fields
		try
		{
			if((AX.getText()!=null && !AX.getText().isEmpty()) &&
				(AY.getText()!=null && !AY.getText().isEmpty())	) {
				Triangulation.getTriangle().setA(new Point2D.Double(((Number)AX.getValue()).doubleValue(),
																((Number)AY.getValue()).doubleValue()));
			}
			if((CX.getText()!=null && !CX.getText().isEmpty())	) {
					Triangulation.getTriangle().setC(new Point2D.Double(((Number)CX.getValue()).doubleValue(),
																	(Triangulation.getTriangle().getC().getY())));
				}
			if(BD.getText()!=null && !BD.getText().isEmpty()) {
					Triangulation.setLengthBD(((Number)BD.getValue()).doubleValue());
				}
			if(CD.getText()!=null && !CD.getText().isEmpty()) {
				Triangulation.setLengthBD(((Number)CD.getValue()).doubleValue());
			}		
		} catch (NumberFormatException e)
		{
			// ignore if not valid
		}
	}

	//
	// Constructor
	//
	public UIFrame() {
	
		init();
	}
	
	private void init() {
		// set size & location based on screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocation((screenSize.width/2)-(DEFAULT_WIDTH/2),(screenSize.height/2)-(DEFAULT_HEIGHT / 2));
		setResizable(false);
		
		setTitle("Triangulation Calculator");
		setLayout(new BorderLayout());
		
		// Define Panels to build coordinate grid
		JPanel mainPanel = new JPanel(); 
		JPanel labelPanel = new JPanel(); // labels
		JPanel Apanel = new JPanel(); // row for A values
		JPanel Bpanel = new JPanel(); // row for B values
		JPanel Cpanel = new JPanel(); // row for C values
		JPanel Dpanel = new JPanel(); // row for pointD values
		labelPanel.setLayout(new GridLayout(1,4));
		Apanel.setLayout(new GridLayout(1,4));
		Bpanel.setLayout(new GridLayout(1,4));
		Cpanel.setLayout(new GridLayout(1,4));
		Dpanel.setLayout(new GridLayout(1,4));
		mainPanel.setLayout(new GridLayout(5,1));
		labelPanel.add(new JLabel("Point"));
		labelPanel.add(new JLabel("X"));
		labelPanel.add(new JLabel("Y"));
		labelPanel.add(new JLabel("Distance from pointD"));
		Apanel.add(new JLabel("A: "));
		
		//Create input text fields which may only be numbers
		Apanel.add(AX = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Apanel.add(AY = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Apanel.add(AD = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Bpanel.add(new JLabel("B: "));
		Bpanel.add(BX = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Bpanel.add(BY = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Bpanel.add(BD = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Cpanel.add(new JLabel("C: "));
		Cpanel.add(CX = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Cpanel.add(CY = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Cpanel.add(CD = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Dpanel.add(new JLabel("pointD: "));
		Dpanel.add(DX = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Dpanel.add(DY = new JFormattedTextField(NumberFormat.getNumberInstance()));
		Dpanel.add(DD = new JFormattedTextField(NumberFormat.getNumberInstance()));
		
		//Set initial values
		AX.setValue(new Double(Triangulation.getTriangle().getA().getX()));
		AY.setValue(new Double(Triangulation.getTriangle().getA().getY()));
		AD.setValue(new Double(0.0));
		BX.setValue(new Double(Triangulation.getTriangle().getB().getX()));
		BY.setValue(new Double(Triangulation.getTriangle().getB().getY()));
		BD.setValue(new Double(Triangulation.getLengthBD()));
		CX.setValue(new Double(Triangulation.getTriangle().getC().getX()));
		CY.setValue(new Double(Triangulation.getTriangle().getC().getY()));
		CD.setValue(new Double(Triangulation.getLengthCD()));
		//calculate initial point D
		Triangulation.triangulate(	Triangulation.getTriangle().getB(),
									Triangulation.getTriangle().getC(),
									(Double)BD.getValue(),(Double)CD.getValue());
		DX.setValue(Triangulation.getD().getX());
		DY.setValue(Triangulation.getD().getY());
		DD.setValue(0.0);
		
		// Some fields are not updateable
		DX.setEditable(false); // pointD values are calculated and output only
		DY.setEditable(false);
		AD.setEditable(false); // no point in having a third updateable distance
		DD.setEditable(false); // by definition 0
		BX.setEditable(false); // point B is fixed at the Origin
		BY.setEditable(false);
		CY.setEditable(false); // point C is fixed on the X-axis	
		
		// Add listeners to text updateable fields
		AX.getDocument().addDocumentListener(fieldListener);
		AY.getDocument().addDocumentListener(fieldListener);
		BD.getDocument().addDocumentListener(fieldListener);
		CX.getDocument().addDocumentListener(fieldListener);
		CY.getDocument().addDocumentListener(fieldListener);
		CD.getDocument().addDocumentListener(fieldListener);
		
		// Build field layout table from sub panels
		mainPanel.add(labelPanel);
		mainPanel.add(Apanel);
		mainPanel.add(Bpanel);
		mainPanel.add(Cpanel);
		mainPanel.add(Dpanel);
		
		// Create buttons with listeners
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(triangulate = new JButton("Triangulate"), BorderLayout.WEST);
		triangulate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Triangulation.triangulate(	Triangulation.getTriangle().getB(),
						Triangulation.getTriangle().getC(),
						(Double)BD.getValue(),(Double)CD.getValue() );
				DX.setValue(Triangulation.getD().getX());
				DY.setValue(Triangulation.getD().getY());
				show.setEnabled(true);
			}
		});
		
		buttonPanel.add(show = new JButton("Visualise"), BorderLayout.EAST);
		show.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Triangulation.getPlotFrame().plot();
				Triangulation.getPlotFrame().setVisible(true);
			}
		});
	
		// Add panels to frame
		add(mainPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
		show.setEnabled(false);
	}
}
