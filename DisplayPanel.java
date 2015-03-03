/* Winnie Lin and Vivienne Shaw
 * CS 230 - Final Project
 * 12/3/2013
 * 
 * DisplayPanel.java 
 * 
 *Creates main Display Panel for GUI with landing lanes, departing lanes, parking spots, and image in background. 
 * Methods to set the landing lane, parking spot, and departing lane to correct picture, and also to reset 
 * the whole display to blank. 
 * 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer; 
import java.util.TimerTask; 

public class DisplayPanel extends JPanel {
  
  //instance variables
  
  //Images
  private ImageIcon sbox, lbox, land, picLand, picPark, picDepart;  
  private Image back = null;
  
  private Airport ap; 
  private MessagePanel message; 

  //text JLabels 
  private JLabel landing, departing,parking; 
  private JLabel lane1,lane2,lane3,lane4;
  private JLabel a1,a2,a3,a4,a5,a6,a7,a8;
  private JLabel b1,b2,b3;
  
  //array of landing lanes
  private JLabel[] l; 
  private JLabel[] d; 
  private JLabel[] park; 
  
  //constructor of DisplayPanel to add all images and text. Takes Airport and MessagePanel object
  public DisplayPanel(Airport airport, MessagePanel message) {
    
    ap = airport; 
    this.message=message;
    
    //initialize JLabel arrays 
    l = new JLabel[4]; 
    d = new JLabel[4]; 
    park = new JLabel[11]; 
    
    //Create images 
    back = new ImageIcon("images/Background.png").getImage();
    lbox = createImageIcon("images/DisplayBoxLarge.png",
                           "Large Empty Box");
    sbox = createImageIcon("images/DisplayBoxSmall.png",
                           "Small Empty Box");
    land = createImageIcon("images/DisplayBoxLargeLandingPlane.png",
                           "Small Empty Box");
        
    setPreferredSize(new Dimension(600,565));
    setLayout(null); //use absolute positioning
    
    //Creates and positions text for JLabels
    JLabel landing = createColoredLabel("Landing","#2952CC", 32, 105, -9); 
    JLabel departing =createColoredLabel("Departing","#2952CC",32,  375, -9); 
    JLabel parking =createColoredLabel("Parking", "#2952CC",32,  250, 330); 
    JLabel lane1 =createColoredLabel("1", "#2952CC",32,  20, 50); 
    JLabel lane2=createColoredLabel("2", "#2952CC",32, 20, 115); 
    JLabel lane3=createColoredLabel("3", "#2952CC",32, 20, 180); 
    JLabel lane4=createColoredLabel("4", "#2952CC",32, 20, 260); 
    
    //creates and positions text for small parking spots 
    JLabel a1=createColoredLabel("A1", "white",23,65, 420); 
    JLabel a2=createColoredLabel("A2", "white",23,130, 420); 
    JLabel a3=createColoredLabel("A3", "white",23,190, 420); 
    JLabel a4=createColoredLabel("A4", "white",23,255, 420); 
    JLabel a5=createColoredLabel("A5", "white",23,323, 420); 
    JLabel a6=createColoredLabel("A6", "white",23,385, 420); 
    JLabel a7=createColoredLabel("A7", "white",23,450, 420); 
    JLabel a8=createColoredLabel("A8", "white",23,515, 420); 
    
    //create and positions boxes for each small parking spot 
    park[0] = createBoxes(sbox, 55, 385);
    park[1] = createBoxes(sbox, 119, 385);
    park[2] = createBoxes(sbox, 182, 385);
    park[3] = createBoxes(sbox, 246, 385);
    park[4] = createBoxes(sbox, 311, 385);
    park[5] = createBoxes(sbox, 375, 385);
    park[6] = createBoxes(sbox, 440, 385);
    park[7] = createBoxes(sbox, 502, 385);
    
    //creates and positions text for large parking spots 
    JLabel b1=createColoredLabel("B1", "white",23,195, 508); 
    JLabel b2=createColoredLabel("B2", "white",23,285, 508); 
    JLabel b3=createColoredLabel("B3", "white",23,375, 508); 
    
    //creates and positions boxes for large parking spots
    park[8] = createBoxes(lbox, 182, 468);
    park[9] = createBoxes(lbox, 270, 468);
    park[10] = createBoxes(lbox, 360, 468);
    
    //creates and positions small landing and departing lanes 
    l[0] = createBoxes(sbox, 130, 45);   
    l[1] = createBoxes(sbox, 130, 110);    
    l[2] = createBoxes(sbox, 130, 180);
    d[0] = createBoxes(sbox, 410, 45);   
    d[1] = createBoxes(sbox, 410, 110);
    d[2] = createBoxes(sbox, 410, 180);  
    
    //creates and positions large landing/departing lanes 
    l[3] = createBoxes(lbox, 128, 255);
    d[3] = createBoxes(lbox, 408, 255);

    //Adds all text, images 
    add(landing);
    add(departing);
    add(parking);
    add(lane1);
    add(lane2);
    add(lane3);
    add(lane4);
    
    add(a1);
    add(a2);
    add(a3);
    add(a4);
    add(a5);
    add(a6);
    add(a7);
    add(a8);
    add(b1);
    add(b2);
    add(b3);
    
    add(l[0]);
    add(l[1]); 
    add(l[2]);    
    add(l[3]); 

    add(d[0]); 
    add(d[1]); 
    add(d[2]); 
    add(d[3]); 

    
    add(park[0]);
    add(park[1]);
    add(park[2]);
    add(park[3]);
    add(park[4]);
    add(park[5]);
    add(park[6]);
    add(park[7]);
    
    add(park[8]);
    add(park[9]);
    add(park[10]);
  }
  
  
  public void setpicLand (ImageIcon temp){
    picLand=temp;
}
  
  //Paints background image onto the panel 
  public void paintComponent(Graphics g) {
    g.drawImage(back, 0, 0, null);
  }
  
  //creates the box using JLabel with image and position 
  private JLabel createBoxes(ImageIcon box, int x, int y){
    JLabel label = new JLabel(box); 
    label.setBounds(x, y, box.getIconWidth(), box.getIconHeight());
    return label;
  }
  
  //creates text JLabel using text, color, and position 
  private JLabel createColoredLabel(String text, String color, int size, int x, int y) {
    JLabel label = new JLabel("<html><center><font color=\"" + color + "\">" + text + "</font></center></html>"); 
    label.setFont(new Font("calibri", Font.BOLD, size));
    label.setBounds(x, y, 150, 50);
    return label;
  }


  //resets all boxes to empty 
  public void setAllEmpty(){
    for(int i = 0; i < l.length; i ++){      //set all landing/departing lanes empty
        if(i == 3){
          l[i].setIcon(lbox);  //sets large boxes empty 
          d[i].setIcon(lbox); 
        }
        else{
          l[i].setIcon(sbox);  //sets small boxes empty 
          d[i].setIcon(sbox); 
        }
      }
  
    for(int i = 0; i < park.length; i++){ //set all parking spots empty 
      if(i>=8)
        park[i].setIcon(lbox); //sets large boxes empty 
      else
        park[i].setIcon(sbox); //sets small boxes empty 
    }
  }
  

   //sets landing lane with airplane picture
    public void setlandLane(Airplane templand, int lane){
      if(!templand.getStatus().equals("Landing")){ //resets to empty box if not landing 
          if(templand.isLarge())
                l[lane].setIcon(lbox); 
              else
                l[lane].setIcon(sbox); 
      }else{
        templand.setLandLane(lane); //sets correct landing lane to airplane
        picLand = templand.getLandPic(); //gets correct landing picture
        ap.getLandLane(lane).useLane(templand);  //sets landing lane in airport to used
        message.setUpdateMessage(ap.organizedChart());  //sets message in messagepanel 
        l[lane].setIcon(picLand); //sets display picture to airplane pic 
        ap.addlandqenqueue(templand); //adds plane to airport landing queue 
      }
    }
    
    //sets parking lane with airplane picture
    public void setparklane(Airplane temppark, int parkspot){
        if(!temppark.getStatus().equals("Parked")){ //sets parking spot to empty if not parked
          if(temppark.isLarge())
          park[parkspot].setIcon(lbox); 
        else
          park[parkspot].setIcon(sbox);
        }else{
          temppark.setParkslot(parkspot); //sets plane's parking spot to correct one 
          picPark = temppark.getParkPic(); //gets parking picture from plane 
          message.setUpdateMessage(ap.organizedChart()); //sets update message
          park[parkspot].setIcon(picPark);  //sets display picture to airplane pic 
        }
      }
    
  //sets departing lane with airplane picture 
    public void setdepartLane(Airplane tempdepart, int lane){
      if((!tempdepart.getStatus().equals("Departing")) && tempdepart.getStatus().equals("Departed")){
        if(tempdepart.isLarge()) //if plane is not departing but is gone, set pic to blank 
          d[lane].setIcon(lbox); 
        else
          d[lane].setIcon(sbox); 
      } else{
        tempdepart.setDepartLane(lane); //set departed lane in airplane
        picDepart = tempdepart.getDepartPic(); //get departing pic from plane 
        ap.getdepartLane(lane).useLane(tempdepart); //use airport depart lane
        message.setUpdateMessage(ap.organizedChart());   //update message panel 
        d[lane].setIcon(picDepart);  //set airplane pic in depart lane 
        ap.getdepartLane(tempdepart.getDepartLane()).clearlane(); //clears depart lane
        tempdepart.setStatus("Departed"); //airplane now "departed"
        ap.departedenqueue(tempdepart); //removes airplane from airport
      }
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

  