/* Author : Winnie Lin and Vivienne Shaw
 * CS 230 - Final Project
 * 12/3/2013
 * 
 * AboutSimulation.java 
 * 
 * Creates "About" panel that informs users of various button functionalities in the simulation. 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AboutSimulation extends JPanel {
  
  //instance variables 
  private Image about;
  private JLabel aboutback; 

  //AboutSimulation constructor 
  public AboutSimulation() {
    about = new ImageIcon("images/About.png").getImage();   //creates image to be painted onto panel 
    setPreferredSize(new Dimension(813,574));   //same size as image 
    setLayout(null); 
    
  }
  
  //paints Image about onto the panel 
  public void paintComponent(Graphics g) {
    g.drawImage(about, 0, 0, null);
  }
  
}