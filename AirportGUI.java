/* Winnie Lin and Vivienne Shaw
 * CS 230 - Final Project
 * 12/3/2013
 * 
 * AirportGUI.java 
 * 
 * Creates the main GUI for the Airport Simulation project. Creates a new Airport object to be referenced from 
 * a new MessagePanel, IncomingFlightsPanel, DisplayPanel, and ControlPanel. Combines all these panels into a 
 * master panel that is put into a JFrame and displayed. Also creates an AboutSimulation panel that is shown to
 * inform the user about the simulation functionalities. 
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AirportGUI extends JPanel {
  
  public static void main (String[] args) {
    
    // creates and shows a Frame of the Airport Simulation
    JFrame frame = new JFrame("Airport");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel master = new JPanel();    //master panel to combine all panels 
    master.setLayout(new BorderLayout(0, 0));
    
    //new airport object to be referenced by all panels 
    Airport airport = new Airport();
    
    //new panels 
    MessagePanel message = new MessagePanel(); 
    IncomingFlightsPanel incom = new IncomingFlightsPanel(airport, message); 
    DisplayPanel display = new DisplayPanel(airport, message); 
    ControlPanel control = new ControlPanel(airport, incom, display, message);
    
    //add all panels to master panel 
    master.add(incom, BorderLayout.CENTER); 
    master.add(control, BorderLayout.LINE_END); 
    master.add(display, BorderLayout.LINE_START); 
    master.add(message, BorderLayout.PAGE_END); 
    
    //add master panel to JFrame 
    frame.getContentPane().add(master);
    frame.setResizable(false);
    
    frame.pack();
    frame.setLocationRelativeTo(null);   //sets frame at center of screen 
    frame.setVisible(true);
    
    //Creates and shows a frame of the about page of the airport simulation 
    //which gives the user the instruction of how to use the airport simulation
    JFrame aboutframe = new JFrame("About"); 
    aboutframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //will not shut down whole program 
    
    //new panel AboutSimulation 
    AboutSimulation about = new AboutSimulation(); 
    
    //master Jpanel for AboutSimulation 
    JPanel aboutmaster = new JPanel(); 

    //adds aboutmaster to JFrame and displays it 
    aboutmaster.add(about); 
    aboutframe.getContentPane().add(aboutmaster); 
    aboutframe.setResizable(false); 
    aboutframe.pack(); 
    aboutframe.setLocationRelativeTo(null); 
    aboutframe.setVisible(true); 
  }  
}
