/* Author : Winnie Lin and Vivienne Shaw
 * CS 230 - Final Project
 * 12/3/2013
 * Airplane.java 
 * This creates Airplane objects. 
 */

import javax.swing.*; 
import java.util.Random;

public class Airplane implements Comparable<Airplane>{
  
  private final int NOT_FOUND=-1; 
  private Random random = new Random ();
  
  private String name; //name of the airplane
  private String size; //size of the airplane
  private String destination; //destination of the airplane coming from and going to
  private String status; //status of the airplane, landing, departing, departed, or parking
  private String airline; //name of the airline
  private int location; //tells us which lane is used land or take off, and also tells us which slot is parked. 
  private final int level; //categorize planes based on its urgency to land 
  
  private ImageIcon picIncom;
  private ImageIcon picLand; 
  private ImageIcon picPark; 
  private ImageIcon picDepart; 
  
  private final String [] SIZE = {"S", "L"};
  private final String [] DESTINATION =  {"Tokyo","San Francicso","New York","Hong Kong","Sydney","Miami", "Los Angeles"}; 
  private final String [] STATUS =  {"Incoming", "Landing", "Parked", "Departing"};
  private final String [] AIRLINES = {"United","Virgin America","jetBlue", "Cathay", "Singapore"};

  private int llane, dlane;  
  private int parkslot;
  private int boxindex = 0; 
  private boolean visited=false;

  private final ImageIcon INCOMLARGE = createImageIcon("images/EmptyBoxLargePlane.png", "Large incoming");
  private final ImageIcon INCOMSMALL = createImageIcon("images/EmptyBoxSmallPlane.png", "Small incoming");
  private final ImageIcon INCOMLARGERED = createImageIcon("images/EmptyBoxLargePlaneRed.png", "Large incoming red");
  private final ImageIcon INCOMSMALLRED = createImageIcon("images/EmptyBoxSmallPlaneRed.png", "Small incoming red");
  
  private final ImageIcon LANDLARGE = createImageIcon("images/DisplayBoxLargeLandingPlane.png", "Large landing");
  private final ImageIcon LANDSMALL = createImageIcon("images/DisplayBoxSmallLandingPlane.png", "Small landing");
  private final ImageIcon LANDLARGERED = createImageIcon("images/DisplayBoxLargeLandingPlaneRed.png", "Large landing red");
  private final ImageIcon LANDSMALLRED = createImageIcon("images/DisplayBoxSmallLandingPlaneRed.png", "Small landing red");
  private final ImageIcon LANDSMALLINLARGE = createImageIcon("images/DisplayBoxLargeSmallLandingPlane.png", "Large landing small");
  private final ImageIcon LANDSMALLINLARGERED = createImageIcon("images/DisplayBoxLargeSmallLandingPlaneRed.png", "Large landing small red");
  
  private final ImageIcon PARKLARGE = createImageIcon("images/DisplayBoxLargePlane.png", "Large parking");
  private final ImageIcon PARKSMALL = createImageIcon("images/DisplayBoxSmallPlane.png", "Small parking");
  private final ImageIcon PARKLARGERED = createImageIcon("images/DisplayBoxLargePlaneRed.png", "Large red parking");
  private final ImageIcon PARKSMALLRED = createImageIcon("images/DisplayBoxSmallPlaneRed.png", "Small red parking");
  
  private final ImageIcon DEPARTLARGE = createImageIcon("images/DisplayBoxLargeDepartingPlane.png", "Large departing");
  private final ImageIcon DEPARTSMALL = createImageIcon("images/DisplayBoxSmallDepartingPlane.png", "Small departing");
  private final ImageIcon DEPARTLARGERED = createImageIcon("images/DisplayBoxLargeDepartingPlaneRed.png", "Large departing red");
  private final ImageIcon DEPARTSMALLRED = createImageIcon("images/DisplayBoxSmallDepartingPlaneRed.png", "Small departing red");
  private final ImageIcon DEPARTSMALLINLARGE = createImageIcon("images/DisplayBoxLargeSmallDepartingPlane.png", "Large departing small");
  private final ImageIcon DEPARTSMALLINLARGERED = createImageIcon("images/DisplayBoxLargeSmallDepartingPlaneRed.png", "Large departing small red");
  
  public Airplane(String n, int rank) {
    status = STATUS[0];
    name = "A" + n;
    location=-1;
    level = rank;
    size = SIZE[random.nextInt(2)];
    destination = DESTINATION[random.nextInt(7)];
    airline = AIRLINES[random.nextInt(5)];
    setPic(); 
    llane = NOT_FOUND;
    dlane = NOT_FOUND;
  }
  
  public Airplane (int count) {
    status = STATUS[0];
    name = "E" + Integer.toString(count);
    location=-1;
    level = 0;
    size = SIZE[random.nextInt(2)];
    destination = DESTINATION[random.nextInt(7)];
    airline = AIRLINES[random.nextInt(5)];
    setPic(); 
    llane = NOT_FOUND;
    dlane = NOT_FOUND;
  }
  
  public Airplane (int count, int rank) {
    status = STATUS[0];
    name = "M" + Integer.toString(count);
    location=-1;
    level = rank;
    size = SIZE[random.nextInt(2)];
    destination = DESTINATION[random.nextInt(7)];
    airline = AIRLINES[random.nextInt(5)];
    setPic(); 
    llane = NOT_FOUND;
    dlane = NOT_FOUND;
  }
  
  
  public ImageIcon getIncomPic(){
    return picIncom; 
  }
  
  public int getboxindex (){
    return boxindex;
  }
  
  public void setboxindex (int i){
    boxindex=i;
  }
  
  
  public boolean isVisited(){
    return visited;
  }
  public void setVisited(boolean a){
    visited=a;
  }
  
  public ImageIcon getLandPic(){
    return picLand; 
  }
  
  public ImageIcon getParkPic(){
    return picPark; 
  }
  
  public ImageIcon getDepartPic(){
    return picDepart; 
  }
  
  public void setPic(){
    if(size.equals("L") && level ==0){
      picIncom = INCOMLARGERED; 
      picLand = LANDLARGERED; 
      picPark = PARKLARGERED; 
      picDepart = DEPARTLARGERED; 
    }
    else if (size.equals("L")){
      picIncom = INCOMLARGE; 
      picLand = LANDLARGE; 
      picPark = PARKLARGE; 
      picDepart = DEPARTLARGE; 
    }
    else if (size.equals("S") && level == 0){
      picIncom = INCOMSMALLRED; 
      picLand = LANDSMALLRED; 
      picPark = PARKSMALLRED; 
      picDepart = DEPARTSMALLRED; 
    }
    else {
      picIncom = INCOMSMALL; 
      picLand = LANDSMALL; 
      picPark = PARKSMALL; 
      picDepart = DEPARTSMALL; 
    }
  }
  
  public int compareTo (Airplane other){
    //compares number of lines of this website with other website
    if (this.level>other.level)
      return 1; 
    else if ( this.level==other.level)
      return 0;
    else return -1;
  }
  
  public String getStatus() {
    return status;
  }
  
  public int getLocation() {
    return location;
  }

  
  public String getSize() {
    return size;
  }
  
  public void setDepartLane(int i){
    dlane=i;
  }
  
  public int getDepartLane() {
    return dlane;
  }
  
  public void setLandLane(int i){
    llane=i;
  }
  
  public int getLandLane() {
    return llane;
  }
  
  public void setStatus (String newstatus) {
    status = newstatus;
  }
  
  public void setLocation (int i){
    location = i;
  }
  
  public String toString() {
    String s ="STATUS: " + status + " ID: " + name + " AIRLINE: " + airline + " CLASS: " + size + " LEVEL: " + level + " DESTINATION: " + destination;
    return s;
  }
  
  private static ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = ControlPanel.class.getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
  
  public void setParkslot(int i){
    parkslot=i;
  }
  
  public int getParkslot(){
    return parkslot;
  }
                          
  
  public boolean isLarge(){
    return (size.equals("L"));
  }
  
  public static void main (String []args) {
  }
  
}