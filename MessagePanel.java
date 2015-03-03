/* Winnie Lin and Vivienne Shaw
 * CS 230 - Final Project
 * 12/3/2013
 * 
 * MessagePanel.java 
 * 
 * Sets blue message panel at bottom with flight information text and cute picture of programmers. 
 * 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MessagePanel extends JPanel {
  
  //instance variables 
  private JLabel message, image; 
  private ImageIcon img; 
  
  //MessagePanel constructor 
  public MessagePanel() {
    
    //creates imageicon from image to set in corner 
    img = createImageIcon("images/WinnieandViv.jpg", "selfie"); 
  
    setLayout(new BorderLayout(0, 0));  
    //creates scrollbar as text increases 
    JScrollPane jsp = new JScrollPane(makeMessagePanel(),JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jsp.setPreferredSize(new Dimension(1020, 100));
    add(jsp, BorderLayout.LINE_END); 
  }
  
  //sets current message, for beginning, special events like add flight, emergency flight 
  public void setMessage(String n) {
    message.setText("<html><center><font color=\"white\">" + n +"</font></center></html>");
    message.setFont(new Font("calibri", Font.BOLD, 30));
  }
  
  //sets update message for new status of flights
   public void setUpdateMessage(String n) {
    message.setText("<html><center><font color=\"white\">" + n +"</font></center></html>");
    message.setFont(new Font("calibri", Font.BOLD, 15));
  }
   
   //create messagepanel panel 
  private JPanel makeMessagePanel(){
    JPanel messagePanel = new JPanel();
    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.LINE_AXIS)); //horizontal line 
    messagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
    messagePanel.setBackground(new Color(0,104,139)); //dark blue 

    //creates initial message to display, centered with calibri 
    message = new JLabel("<html><center><font color=\"white\">Welcome to Winnie and Vivienne's Airport. Press Start to begin the simulation.</font></center></html>"); 
    message.setFont(new Font("calibri", Font.BOLD, 26));
    message.setHorizontalAlignment(SwingConstants.CENTER);
    message.setAlignmentX(Component.CENTER_ALIGNMENT);
   
    //places image in corner
    image = new JLabel(img);      
    messagePanel.add(Box.createRigidArea(new Dimension(9,5)));
    messagePanel.add(image); 
    messagePanel.add(Box.createRigidArea(new Dimension(5,5)));
    messagePanel.add(message);
   
    return messagePanel; 
  }
  
 
  /** 
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