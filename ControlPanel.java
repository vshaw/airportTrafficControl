/* Winnie Lin and Vivienne Shaw
 * CS 230 - Final Project
 * 12/3/2013
 * 
 * ControlPanel.java 
 * 
 * Creates the ControlPanel for the main GUI. Also assigns listeners to the start, pause, stop, add flight, 
 * emergency landing, rain, and thunderstorm buttons. Uses a timer to continuously add flights to the incoming
 * flights panel, land, park and depart. Uses a timer actionlistener for each lane and parking spot to properly
 * queue or dequeue incoming/landing/parking/departing flights. 
 * 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import javafoundations.*;
import java.io.PrintWriter; 
import java.io.IOException; 
import java.sql.Timestamp; 
import java.util.Date; 

public class ControlPanel extends JPanel {
  
  //instance vars
  private Airport airport;
  private IncomingFlightsPanel incom; 
  private DisplayPanel display; 
  private MessagePanel message; 
  
  private Timer timer;
  private Timer timer1;
  private int count=1;
  private boolean go; 
  
  //image icons 
  private ImageIcon startImg, pauseImg, stopImg, addImg, emerImg, rain, thunder; 
  
  //Jbuttons and JLabels 
  private JButton start, pause, stop, addFlight, emergency, rainbutton, thunderbutton; 
  private JLabel control, flights, weather, incoming; 
  
  
  //ControlPanel constructor. Takes Airport, IncomingFlightsPanel, DisplayPanel and MessagePanel objects
  public ControlPanel(Airport ap, IncomingFlightsPanel i, DisplayPanel d, MessagePanel m) {
    airport = ap; 
    incom = i; 
    display = d; 
    message = m; 
    
    //calls method to create ImageIcon for each picture
    rain = createImageIcon("images/Rain.png", "rain button"); 
    thunder = createImageIcon("images/Thunderstorm.png", "thunder button"); 
    startImg = createImageIcon("images/Start.png", "Start button");
    pauseImg = createImageIcon("images/Pause.png", "Pause button");
    stopImg = createImageIcon("images/Stop.png", "Stop button");
    addImg = createImageIcon("images/AddFlight.png","Add Flight button");
    emerImg = createImageIcon("images/EmergencyLanding.png","Emergency Landing button");
    
    setLayout(new BorderLayout(0, 0)); 
    setBackground(Color.blue); 
    add(makeControlPanel(), BorderLayout.LINE_END);  
  }
  
  //creates the main ControlPanel panel 
  private JPanel makeControlPanel() {
    JPanel controlPanel = new JPanel();
    
    //lays out objects in vertical line
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS)); 
    
    controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    Dimension d = new Dimension(220,565);
    controlPanel.setPreferredSize(d);    //creates JPanel of specific size Dimension d   
    
    //writes "Control Panel" centered with calibri font size 32
    control = new JLabel("<html><center><font color=\"#2952CC\">Control Panel</font></center></html>"); 
    control.setFont(new Font("calibri", Font.BOLD, 32));
    control.setHorizontalAlignment(SwingConstants.CENTER);
    control.setAlignmentX(Component.CENTER_ALIGNMENT);
    control.setMaximumSize(new Dimension(200, 40));
    
    //writes "Flights" centered with calibri font size 32
    flights = new JLabel("<html><p style=\"padding:2\"><font color=\"#2952CC\">Flights</font></p></html>"); 
    flights.setFont(new Font("calibri", Font.BOLD, 32));
    flights.setAlignmentX(Component.CENTER_ALIGNMENT);
    flights.setMaximumSize(new Dimension(220, 40));
    
    //writes "Weather" centered with calibri font size 32
    weather = new JLabel("<html><p style=\"padding:2\"><font color=\"#2952CC\">Weather</font></p></html>"); 
    weather.setFont(new Font("calibri", Font.BOLD, 32));
    weather.setAlignmentX(Component.CENTER_ALIGNMENT);
    weather.setMaximumSize(new Dimension(220, 40));
    
    //calls makebutton method to create the 7 buttons 
    start = makeButton (startImg);
    pause = makeButton (pauseImg);
    stop = makeButton (stopImg);
    addFlight =makeButton (addImg);
    emergency = makeButton(emerImg);
    rainbutton = makeButton(rain); 
    thunderbutton = makeButton(thunder); 
    
    //Adds all Jlabels, and JButtons to panel, with RigidArea in between for spacing 
    controlPanel.add(control); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,20)));
    controlPanel.add(start); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,5)));
    controlPanel.add(pause); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,5)));
    controlPanel.add(stop); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,14)));
    controlPanel.add(flights); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,10)));
    controlPanel.add(addFlight); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,10)));
    controlPanel.add(emergency); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,10)));
    controlPanel.add(weather); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,10)));
    controlPanel.add(rainbutton); 
    controlPanel.add(Box.createRigidArea(new Dimension(5,10)));
    controlPanel.add(thunderbutton); 
    
    return controlPanel; 
  }
  
  //method creates a button with an image 
  private JButton makeButton (ImageIcon img){
    JButton button = new JButton(img); 
    button.setBorder(null); 
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.addActionListener (new ButtonListener());
    return button;
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
  
  //Button listeners for all buttons 
  private class ButtonListener implements ActionListener 
  { 
    
    public void actionPerformed(ActionEvent event)
    {
      Object source = event.getSource(); 
      if(source == start){   //Starts simulation 
        go=true;  //boolean to remember simulation is running 
        
        //Timer objects to keep simulation running 
        timer= new Timer(1000, null);
        timer1 = new Timer (4000, null);
        
        //new ActionListener to continuously add random incoming flights
        ActionListener taskPerformer = new ActionListener() {          
          public void actionPerformed(ActionEvent evt) {
            Airplane a = new Airplane(Integer.toString(count),1); 
            airport.addincomingflights(a); 
            if(a.getStatus().equals("Incoming") && go){  //sets display to image 
              incom.setBox(a); 
              Date date = new Date(); //adds timestamp to flight summary 
              airport.addSummary(new Timestamp(date.getTime()) + " " + a.toString()); 
            }
            count++; 
          }
        };
        
        //new ActionListener to get flight from incomingflights and add to empty lane 1
        ActionListener landtaskPerformer = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (airport.hasIncoming()){
              Airplane temp = airport.getIncoming();  //take airplane from incoming 
              if ( !airport.getLandLane(0).checkLane()){  //make sure lane is empty 
                if (!temp.getStatus().equals("Landing") && !temp.isLarge()){ //lane only for small planes 
                  airport.deleteIncoming(); //deletes plane from incoming queue 
                  airport.land(temp, 0); 
                  Date date = new Date();  //adds to flight to summary with timestamp 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  incom.setBox(temp);    //sets incoming and display to correct pics
                  display.setlandLane(temp, 0);   //records land lane in display and plane 
                  temp.setLandLane(0);
                  airport.getLandLane(0).useLane(temp); //records land lane in airport object
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from incomingflights and add to empty lane 2 
        ActionListener landtaskPerformer1 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (airport.hasIncoming()){
              Airplane temp = airport.getIncoming(); 
              if (temp!=null && !airport.getLandLane(1).checkLane()){
                if (!temp.getStatus().equals("Landing") && !temp.isLarge()){
                  airport.deleteIncoming();
                  airport.land(temp, 1); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  incom.setBox(temp); 
                  display.setlandLane(temp, 1);
                  temp.setLandLane(1);
                  airport.getLandLane(1).useLane(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from incomingflights and add to empty lane 3 
        ActionListener landtaskPerformer2 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (airport.hasIncoming()){
              Airplane temp = airport.getIncoming(); 
              if (temp!=null && !airport.getLandLane(2).checkLane()){
                if (!temp.getStatus().equals("Landing") && !temp.isLarge()){
                  airport.deleteIncoming();
                  airport.land(temp, 2); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  incom.setBox(temp); 
                  display.setlandLane(temp, 2);
                  temp.setLandLane(2);
                  airport.getLandLane(2).useLane(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from incomingflights and add to empty lane 4   
        ActionListener landtaskPerformer3 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (airport.hasIncoming()){
              Airplane temp = airport.getIncoming(); 
              if (temp!=null && !airport.getLandLane(3).checkLane()){
                if ((!temp.getStatus().equals("Landing")) && temp.isLarge()){  //LANE FOR LARGE PLANES
                  airport.deleteIncoming();
                  airport.land(temp, 3); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  incom.setBox(temp); 
                  display.setlandLane(temp, 3);
                  temp.setLandLane(3);
                  airport.getLandLane(3).useLane(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from landing lane and park in spot 1
        ActionListener parktaskPerformer = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (!airport.islandqEmpty()){   //finds flight in landing lane 
              Airplane temp = airport.getlandqdequeue();  //gets plane from landing lane 
              if(temp!=null && !airport.checkpslot(0)){   //checks if parkingspot is empty 
                if(!temp.getStatus().equals("Parked") && !temp.isLarge()){
                  airport.landqdequeue(); //dequeues plane from land queue
                  airport.park(temp, 0);  //parks plane in spot 0 
                  Date date = new Date();  //adds timestamp to summary 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setlandLane(temp, temp.getLandLane()); //removes plane from lane in GUI
                  display.setparklane(temp, 0);  //shows plane in parking spot in GUI
                  airport.getLandLane(temp.getLandLane()).clearlane(); // clears previous land lane 
                  temp.setParkslot(0); //stores spot in airplane object
                  airport.parkqenqueue(temp);  //stores airplane in airport parking spot
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from landing lane and park in spot 2
        ActionListener parktaskPerformer1 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (!airport.islandqEmpty()){
              Airplane temp = airport.getlandqdequeue();
              if(temp!=null && !airport.checkpslot(1)){
                if(!temp.getStatus().equals("Parked") && !temp.isLarge()){
                  airport.landqdequeue();
                  airport.park(temp, 1); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setlandLane(temp, temp.getLandLane());
                  display.setparklane(temp, 1); 
                  airport.getLandLane(temp.getLandLane()).clearlane();
                  temp.setParkslot(1);
                  airport.parkqenqueue(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from landing lane and park in spot 3
        ActionListener parktaskPerformer2 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (!airport.islandqEmpty()){
              Airplane temp = airport.getlandqdequeue();
              if(temp!=null && !airport.checkpslot(2)){
                if(!temp.getStatus().equals("Parked") && !temp.isLarge()){
                  airport.landqdequeue();
                  airport.park(temp, 2); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setlandLane(temp, temp.getLandLane());
                  display.setparklane(temp, 2); 
                  airport.getLandLane(temp.getLandLane()).clearlane();
                  temp.setParkslot(2);
                  airport.parkqenqueue(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from landing lane and park in spot 4
        ActionListener parktaskPerformer3 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (!airport.islandqEmpty()){
              Airplane temp = airport.getlandqdequeue();
              if(temp!=null && !airport.checkpslot(3)){
                if(!temp.getStatus().equals("Parked") && !temp.isLarge()){
                  airport.landqdequeue();
                  airport.park(temp, 3); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setlandLane(temp, temp.getLandLane());
                  display.setparklane(temp, 3); 
                  airport.getLandLane(temp.getLandLane()).clearlane();
                  temp.setParkslot(3);
                  airport.parkqenqueue(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from landing lane and park in spot 9
        ActionListener parktaskPerformer8 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (!airport.islandqEmpty()){
              Airplane temp = airport.getlandqdequeue();
              if(temp!=null && !airport.checkpslot(8)){
                if((!temp.getStatus().equals("Parked")) && temp.isLarge()){ //FOR LARGE PLANES
                  airport.landqdequeue();
                  airport.park(temp, 8); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setlandLane(temp, temp.getLandLane());
                  display.setparklane(temp, 8); 
                  airport.getLandLane(temp.getLandLane()).clearlane();
                  temp.setParkslot(8);
                  airport.parkqenqueue(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from landing lane and park in spot 10
        ActionListener parktaskPerformer9 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (!airport.islandqEmpty()){
              Airplane temp = airport.getlandqdequeue();
              if(temp!=null && !airport.checkpslot(9)){
                if((!temp.getStatus().equals("Parked")) && temp.isLarge()){ //FOR LARGE PLANES 
                  airport.landqdequeue();
                  airport.park(temp, 9); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setlandLane(temp, temp.getLandLane());
                  display.setparklane(temp, 9); 
                  airport.getLandLane(temp.getLandLane()).clearlane();
                  temp.setParkslot(9);
                  airport.parkqenqueue(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener to get flight from landing lane and park in spot 11
        ActionListener parktaskPerformer10 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if (!airport.islandqEmpty()){
              Airplane temp = airport.getlandqdequeue();
              if(temp!=null && !airport.checkpslot(10)){
                if((!temp.getStatus().equals("Parked")) && temp.isLarge()){ //FOR LARGE PLANES
                  airport.landqdequeue();
                  airport.park(temp, 10); 
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setlandLane(temp, temp.getLandLane());
                  display.setparklane(temp, 10); 
                  airport.getLandLane(temp.getLandLane()).clearlane();
                  temp.setParkslot(10);
                  airport.parkqenqueue(temp);
                }
              }
            }
          }
        };
        
        //new ActionListener for depart lane 1 
        ActionListener departtaskPerformer = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {       
            if(!airport.isparkqEmpty()){ //checks for plane in parking spot 
              Airplane temp = airport.getparkqdequeue(); //gets plane from parking spot 
              if (temp!=null && !airport.getdepartLane(0).checkLane()){ //checks depart lane is empty
                if ((!temp.getStatus().equals("Departing")) && !temp.isLarge()){ //small planes only
                  airport.parkqdequeue(); //removes plane from parking spot in airport
                  airport.depart(temp, 0); //departs plane from airport
                  Date date = new Date();  //records timestamp of activity 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setparklane(temp, temp.getParkslot()); //removes pic from parking spot 
                  display.setdepartLane(temp, 0); //adds pic in departing lane 
                  temp.setDepartLane(0); //sets depart lane in airplane object
                  
                }
              }
            }
          }
        };
        
        //new ActionListener for depart lane 2
        ActionListener departtaskPerformer1 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if(!airport.isparkqEmpty()){
              Airplane temp = airport.getparkqdequeue();
              if (temp!=null && !airport.getdepartLane(1).checkLane()){
                if ((!temp.getStatus().equals("Departing")) && !temp.isLarge()){
                  airport.parkqdequeue();
                  airport.depart(temp, 1);
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setparklane(temp, temp.getParkslot());
                  display.setdepartLane(temp, 1);
                  temp.setDepartLane(1);
                  
                }
              }
            }
          }
          
        };
        
        //new ActionListener for depart lane 3
        ActionListener departtaskPerformer2 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if(!airport.isparkqEmpty()){
              Airplane temp = airport.getparkqdequeue();
              if (temp!=null && !airport.getdepartLane(2).checkLane()){
                if ((!temp.getStatus().equals("Departing")) && !temp.isLarge()){
                  airport.parkqdequeue();
                  airport.depart(temp, 2);
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setparklane(temp, temp.getParkslot());
                  display.setdepartLane(temp, 2);
                  temp.setDepartLane(2);
                  
                }
              }
            }
          }
          
        };
        
        //new ActionListener for depart lane 4
        ActionListener departtaskPerformer3 = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            if(!airport.isparkqEmpty()){             
              Airplane temp = airport.getparkqdequeue();
              if (temp!=null && !airport.getdepartLane(3).checkLane()){           
                if ((!temp.getStatus().equals("Departing")) && temp.isLarge()){ //LARGE PLANES ONLY
                  airport.parkqdequeue();
                  airport.depart(temp, 3);
                  Date date = new Date(); 
                  airport.addSummary(new Timestamp(date.getTime()) + " " + temp.toString());
                  display.setparklane(temp, temp.getParkslot());
                  display.setdepartLane(temp, 3);
                  temp.setDepartLane(3);
                  
                }
              }
            }
          }
          
        };
        
        //new ActionListener to delete departing planes
        ActionListener deletedeparttaskPerformer = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            while (!airport.isdepartedqEmpty()){ //checks if there is a plane in departed queue
              Airplane temp = airport.getdepartedqdequeue(); //gets plane from departed queue
              if (temp!=null){
                int d = temp.getDepartLane(); //gets departing lane
                display.setdepartLane(temp, d);  //removes pic from departing lane
                airport.delete(temp); //permanently deletes plane from airport system
              }
            } 
          }
        };
        
        //add taskPerformer actionListener to timer to keep adding planes 
        timer.addActionListener(taskPerformer);
        
        //adds landing planes ActionListeners
        timer1.addActionListener(landtaskPerformer);
        timer1.addActionListener(landtaskPerformer1);
        timer1.addActionListener(landtaskPerformer2);
        timer1.addActionListener(landtaskPerformer3);
        
        //Adds parking planes ActionListeners
        timer1.addActionListener(parktaskPerformer);  //small spots
        timer1.addActionListener(parktaskPerformer1);
        timer1.addActionListener(parktaskPerformer2);
        timer1.addActionListener(parktaskPerformer3);
        
        timer1.addActionListener(parktaskPerformer8);  //large spots
        timer1.addActionListener(parktaskPerformer9);
        timer1.addActionListener(parktaskPerformer10);
        
        //Adds departing planes ActionListeners
        timer1.addActionListener(departtaskPerformer);
        timer1.addActionListener(departtaskPerformer1);
        timer1.addActionListener(departtaskPerformer2);
        timer1.addActionListener(departtaskPerformer3);
        
        //adds delete departing planes ActionListeners
        timer1.addActionListener(deletedeparttaskPerformer);
        
        //sets delay for each timer 
        timer.setDelay(3000);
        timer1.setDelay(3001);
        
        //start each timer, with repeats enabled 
        timer.setRepeats(true);
        timer.start();
        timer1.setRepeats(true);
        timer1.start();
        
        
      }
      
      //if pause button is pressed while simulation is running stop timers
      if (go && source == pause){
        go=false;
        timer.stop();
        timer1.stop();
      }
      
      //if stop button is pressed while simulation is running stop timers, write file 
      if (go && source == stop){
        go=false;
        timer.stop(); 
        timer1.stop();
        try{  //try catch block to write to file flight summary 
          PrintWriter writer = new PrintWriter("FlightSummary.html", "UTF-8"); 
          writer.println(airport.getSummary()); 
          writer.close(); 
        }
        catch (IOException ex){
          ex.printStackTrace(); 
        }
        //show dialog to inform user of new file 
        JOptionPane.showMessageDialog(null,"Simulation summary written to \"Flight Summary.html\"");
        
        display.setAllEmpty(); //set gui display to empty 
        incom.setAllEmpty(); 
        airport.resetAirport(); //clears all airport queues 
        message.setUpdateMessage("");
        count = 1; 
      }
      
      //if emergencylanding is pressed while simulation is running 
      if (go && source == emergency){
        Date date = new Date(); //add to flight summary 
        airport.addSummary(new Timestamp(date.getTime()) + " EMERGENCY LANDING. " );
        Airplane a = new Airplane(count); //new airplane of emergency status 
        airport.addincomingflights(a);  //add to incoming flights
        if(a.getStatus().equals("Incoming") && go){ 
          incom.setBox(a); //shows pic in incomingflights panel 
        }
        count++; 
        message.setMessage("You have added an emergency plane for landing.");
      }
      
      //if Add Flight is pressed while simulation is running 
      if (go && source == addFlight){
        Airplane a = new Airplane(count, 1); 
        airport.addincomingflights(a); //create new airplane and add to incoming flights
        if(a.getStatus().equals("Incoming") && go){
          incom.setBox(a);  //set display panel to pic 
        }
        count++; 
        message.setMessage("You have manually added a plane for landing.");
      }
      
      //if Rain is pressed while simulation is running 
      if (go && source == rainbutton){
        Date date = new Date(); //add to flight summary 
        airport.addSummary(new Timestamp(date.getTime()) + " RAIN DELAY 5 SECONDS. " );
        try{  //pauses whole system for 5 seconds 
          Thread.sleep(5000); 
        }
        catch (InterruptedException i){
          i.printStackTrace(); 
        }
      }
      
      //if Thunderstorm is pressed while simulation is running 
      if(go && source == thunderbutton){
        Date date = new Date(); //add to flight summary 
        airport.addSummary(new Timestamp(date.getTime()) + " THUNDERSTORM DELAY 10 SECONDS. " );
        try{ //pauses whole system for 10 seconds 
          Thread.sleep(10000); 
        }
        catch (InterruptedException i){
          i.printStackTrace(); 
        }
      }
      
    }
  }
}

