/* Winnie Lin and Vivienne Shaw
 * CS 230 Final Project
 * Airport.java
 */
import java.util.LinkedList;
import java.util.PriorityQueue;
import javafoundations.*;

public class Airport {
  //instance variables
  private final int NUM_SMALLLANE = 3; //number of small lanes for small airplanes
  private final int NOT_FOUND = -1; 
  private final int NUM_PARKINGA = 8; //number of parking slots for small airplanes
  private final int NUM_PARKINGB = 3; //number of parking slots for large airplanes
  
  private String summary; //stores the summary of a simulation
  
  private Lane [] landlanes; 
  private Lane [] departlanes;
  private LinkedList <LinearNode<Airplane>> parkinglot;
  
  private PriorityQueue<Airplane> pqueue; //stores the incoming airplanes based on its urgency to land
  private LinkedList<Airplane> inAirport; //stores all of the airplanes that are in this airport
  
  private LinkedQueue<Airplane> departed; //stores the airplanes that departed from our airport
  private LinkedQueue<Airplane> landq; //stores the landed airplanes that are ready to park
  private LinkedQueue<Airplane> parkq; //stores the parked airplanes that are ready to depart
  
  //constructor
  public Airport() {
    summary = ""; 
    landlanes = new Lane[4]; 
    departlanes = new Lane[4]; 
    storeLanes(landlanes);
    storeLanes(departlanes);
    
    inAirport = new LinkedList<Airplane> ();
    departed= new LinkedQueue<Airplane>();
    landq= new LinkedQueue<Airplane>();
    parkq= new LinkedQueue<Airplane>();
    pqueue = new PriorityQueue<Airplane>();
    
    parkinglot = new LinkedList <LinearNode<Airplane>>();
    storeParkingLot (parkinglot, NUM_PARKINGA+NUM_PARKINGB);
  }
    
  public void resetAirport(){
    summary = ""; 
    landlanes = new Lane[4]; 
    departlanes = new Lane[4]; 
    storeLanes(landlanes);
    storeLanes(departlanes);
    
    inAirport = new LinkedList<Airplane> ();
    departed= new LinkedQueue<Airplane>();
    landq= new LinkedQueue<Airplane>();
    parkq= new LinkedQueue<Airplane>();
    pqueue = new PriorityQueue<Airplane>();
    
    parkinglot = new LinkedList <LinearNode<Airplane>>();
    storeParkingLot (parkinglot, NUM_PARKINGA+NUM_PARKINGB);
  }
  
  
  private void storeLanes(Lane [] lanes){
    for (int i = 0; i<NUM_SMALLLANE; i++)
      lanes[i] = new Lane ("S");
    lanes[3] = new Lane("L");
  }
  
  
  public void adddeparted (Airplane temp){
    departed.enqueue(temp);
  }
  
  
  public Airplane getdepartedqdequeue(){
    return departed.dequeue();
  }
  
  public boolean isdepartedqEmpty(){
    return departed.isEmpty();
  }
  
  public void departedenqueue(Airplane temp){
    departed.enqueue(temp);
  }
  
  public LinkedQueue<Airplane> getlandq(){
    return landq;
  }
  
  public void addlandqenqueue(Airplane temp){
    landq.enqueue(temp);
  }
  
  public  boolean islandqEmpty(){
    return landq.isEmpty();
  }
  
  public Airplane getlandqdequeue(){
    return landq.first();
  }
  
  public void landqdequeue(){
    landq.dequeue();
  }
  
  public boolean isparkqEmpty(){
    return parkq.isEmpty();
  }
  
  public Airplane getparkqdequeue(){
    return parkq.first();
  }
  
  public void parkqdequeue(){
    parkq.dequeue();
  }
  
  public void parkqenqueue(Airplane temp){
    parkq.enqueue(temp);
  }
  
  private void storeParkingLot (LinkedList <LinearNode<Airplane>> lot, int size) {
    for (int i = 0; i<size; i++)
      lot.add(i, new LinearNode<Airplane>());
  }
  
  
  public void setParkingLot (int i, Airplane name){
    parkinglot.get(i).setElement(name);
  }
  
  /* adds an airplane that will be landing in this airport
   * @param name - the airplane that is added 
   */
  public void addincomingflights(Airplane name) {
    pqueue.add(name);
    inAirport.add(name);
  }
  
 /* Updates the airport traffic control 
  * param name - name of the Airplane that is being parked
  * param slot - index of the parking slot that name will be parked in
  */
  public void park(Airplane name, int slot) {
    landlanes[name.getLocation()].clearlane();     
    parkinglot.get(slot).setElement(name);
    name.setStatus("Parked");
    name.setLocation(slot);
  }
  
  /* checks if a parking slot is open
   * param i -  the index of the parking slot
   * returns true if the parking slot is open for parking
   * returns false if the parking slot is not open for parking
   */
  public boolean checkpslot(int i) {
    return !(parkinglot.get(i).isEmpty());
  }
  
  /* returns the first airplane from the incoming queue 
   */
  public Airplane getIncoming() {
    Airplane temp = pqueue.peek(); 
    return temp;
  }
  
  /* Removes the first airplane from the incoming queue that has 
   * scheduled to land.
   */
  public void deleteIncoming() {
    pqueue.poll();
  }
  
  
  /* Checks if there is an incoming flight waiting to land.
   * returns true if there is, 
   * returns false if there isn't
   */
  public boolean hasIncoming() {
    Airplane temp = pqueue.peek(); 
    return (temp!=null);
  }
  
  
  public void land (Airplane name, int lane ) {
    landlanes[lane].useLane(name);
    name.setStatus("Landing");
    name.setLocation(lane);
  }

    
  /* Removes an airplane that is not in this airport
   * @param name - the airplane that is removed
   */
  public void delete(Airplane name){
    inAirport.remove(name);
  }
  
  public Lane getLandLane(int i){
    return landlanes[i];
  }
  
  public Lane getdepartLane(int i){
    return departlanes[i];
  }
  
    
  /* Updates the airport system of the airplane that is departing 
   * and the lane that the airplane is using to depart
   * @param name - the airplane that is departing
   * @param lane - the index of the lane which the airplane is using to depart
   */
  public void depart (Airplane name, int lane){
    departlanes[lane].useLane(name);
    parkinglot.get(name.getLocation()).setElement(null);
    name.setStatus("Departing");
    name.setLocation(lane);
  }
  
  /* Returns the summary of the simulation that is stored in a string
   */
  public String getSummary(){
    return summary; 
  }
  
  /* Organizes the inAirport queue, which is used to provide information 
   * of airplanes in the airport in the message panel
   */
  public String organizedChart(){
    String s = "";
    for( int i =0; i<inAirport.size(); i++){
      s+=inAirport.get(i)+"<br>";
    }
    return s;
  }
  
  /* Adds information of an airplane to the summary 
   * @param n - string of information about an airplane
   */
  public void addSummary(String n){
    summary = summary + n + "<br><br>"; 
  }
  
  public String toString() {
    String info = "LANDING LANES: \n";
    for (int i = 0;  i<4; i++)
      info += "LANE " + i + " " + landlanes[i].getLane() + "\n";
    
    for (int i = 0;  i<4; i++)
      info += "LANE " + i + departlanes[i].getLane() + "\n"; 
    
    info += "\n" + "PARKING LOT SMALL" +"\n" ;
    for (int i = 0;  i<NUM_PARKINGA; i++)
      info += parkinglot.get(i).getElement() + "\n";
    
    info += pqueue; 
    return info;
  }
  
  //for testing purporses
  public static void main (String [] args) {
    Airport sfo = new Airport ();
    Airplane winnie = new Airplane ("Winnie", 1);
    Airplane vivienne = new Airplane ("Vivienne",2);
    Airplane tiff = new Airplane ("Tiffany",3);
    Airplane kel = new Airplane ("Kelly",4);
    Airplane bob = new Airplane ("Bob",5);
    Airplane nancy = new Airplane ("Nancy",6);
    Airplane wendy = new Airplane ("Wendy",7);
    Airplane queen = new Airplane ("Quyen",8);
    Airplane em = new Airplane ("Emily",9);
    
    sfo.addincomingflights(winnie);   
    sfo.addincomingflights(vivienne); 
    sfo.addincomingflights(tiff);  
    sfo.addincomingflights(kel);   
    sfo.addincomingflights(bob);   
    sfo.addincomingflights(nancy);   
    sfo.addincomingflights(wendy);   
    sfo.addincomingflights(queen);  
    sfo.addincomingflights(em);   
    System.out.println(sfo.pqueue);
    
  } 
}