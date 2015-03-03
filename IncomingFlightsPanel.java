/* Winnie Lin and Vivienne Shaw
 * CS 230 - Final Project
 * 12/3/2013
 * 
 * IncomingFlightsPanel.java 
 * 
 * Creates IncomingFlightsPanel that shows 5 of the incoming flights. 
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IncomingFlightsPanel extends JPanel {
  
  //instance vars
  private ImageIcon box, large, small, largered, smallred; 
  private JLabel[] incom; 
  private JLabel incoming; 
  private MessagePanel message;
  private Airport ap;
  
  //IncomingFlightsPanel constructor. Takes airport and MessagePanel objects 
  public IncomingFlightsPanel(Airport ap, MessagePanel message) {
    this.message=message;
    this.ap=ap;
    
    //initializes JLabel array 
    incom = new JLabel[5]; 
    
    //creates image icons 
    box = createImageIcon("images/EmptyBox.png",
                          "Empty Box");
    
    large = createImageIcon("images/EmptyBoxLargePlane.png",
                            "Empty Box Large Plane");
    
    small = createImageIcon("images/EmptyBoxSmallPlane.png",
                            "Empty Box Small Plane");
    
    largered = createImageIcon("images/EmptyBoxLargePlaneRed.png",
                               "Empty Box Large Red Plane");
    
    smallred = createImageIcon("images/EmptyBoxSmallPlaneRed.png",
                               "Empty Box Small Red Plane");
    
    setLayout(new BorderLayout(0, 0)); 
    setBackground(Color.blue); // to match the background color of center grid panel
    
    add(makeIncomingPanel(), BorderLayout.CENTER); 
  }
  
  //reset method, makes all boxes blank 
  public void setAllEmpty(){
    for(int i = 0; i < incom.length; i++)
      incom[i].setIcon(box);    
  }
    
  //checks if a box is empty 
  public boolean checkEmpty() {
    for(int i = 0; i < incom.length; i++) //loops through all boxes 
      if(incom[i].getIcon()==box){
      return true;
    } 
    return false;
  }
      
  //sets box with correct picture. takes airplane parameter
  public void setBox(Airplane a){
    if(!a.isVisited()){  //gets next empty box 
      for(int i = 0; i < incom.length; i++){
      if(incom[i].getIcon() == box){
        a.setboxindex(i);  
        break; 
      }
    }
    }
    if(!a.getStatus().equals("Incoming")){ //sets box empty if plane left 
      int index = a.getboxindex();
      incom[index].setIcon(box); 
      a.setVisited(false); 
    }else{
       if (checkEmpty()){ //update message in MessagePanel 
          message.setUpdateMessage(ap.organizedChart()); 
      if(a.getSize().equals("L")){ //sets large pic in box 
        incom[a.getboxindex()].setIcon(a.getIncomPic());
        a.setVisited(true);
      }
      if(a.getSize().equals("S")){ //sets small pic in box 
        incom[a.getboxindex()].setIcon(a.getIncomPic()); 
        a.setVisited(true);
      }
       }
    }
  }
  
  //creates JPanel of incomingflights 
  private JPanel makeIncomingPanel() {
    
    JPanel incomingPanel = new JPanel(); 
    incomingPanel.setLayout(new BoxLayout(incomingPanel, BoxLayout.PAGE_AXIS)); //vertical line
    incomingPanel.setBorder(BorderFactory.createLineBorder(Color.black)); //black border
    Dimension d = new Dimension(200,565);
    incomingPanel.setPreferredSize(d); //sets the dimension of the incoming panel 
    
    //sets text of "Incoming Flights" to centered calibri 32 size font
    incoming = new JLabel("<html><center><font color=\"#2952CC\">Incoming Flights</font></center></html>"); 
    incoming.setFont(new Font("calibri", Font.BOLD, 32));
    incoming.setHorizontalAlignment(SwingConstants.CENTER);
    incoming.setAlignmentX(Component.CENTER_ALIGNMENT);
    incoming.setMaximumSize(new Dimension(220, 85));
    
    //sets all incoming boxes in center 
    incom[0] = new JLabel(box); 
    incom[0].setAlignmentX(Component.CENTER_ALIGNMENT);
    
    incom[1] = new JLabel(box); 
    incom[1].setAlignmentX(Component.CENTER_ALIGNMENT);
    
    incom[2] = new JLabel(box); 
    incom[2].setAlignmentX(Component.CENTER_ALIGNMENT);
    
    incom[3] = new JLabel(box); 
    incom[3].setAlignmentX(Component.CENTER_ALIGNMENT);
    
    incom[4] = new JLabel(box); 
    incom[4].setAlignmentX(Component.CENTER_ALIGNMENT);
    
    //adds all text and boxes to panel, with rigidarea inbetween 
    incomingPanel.add(incoming); 
    incomingPanel.add(Box.createRigidArea(new Dimension(5,20)));
    incomingPanel.add(incom[0]); 
    incomingPanel.add(Box.createRigidArea(new Dimension(5,20)));
    incomingPanel.add(incom[1]); 
    incomingPanel.add(Box.createRigidArea(new Dimension(5,20)));
    incomingPanel.add(incom[2]); 
    incomingPanel.add(Box.createRigidArea(new Dimension(5,20)));
    incomingPanel.add(incom[3]); 
    incomingPanel.add(Box.createRigidArea(new Dimension(5,20)));
    incomingPanel.add(incom[4]); 
    
    return incomingPanel; 
  }

  /*
   * Creates and returns an ImageIcon out of an image file.
   * @param path The path to the imagefile relevant to the current file.
   * @param description A short description to the image.
   * @return ImageIcon An ImageIcon, or null if the path was invalid. 
   */
  private static ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = ControlPanel.class.getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
} 
