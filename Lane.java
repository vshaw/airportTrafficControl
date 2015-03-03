/* Author : Winnie Lin and Vivienne Shaw
 * CS 230 - Final Project
 * 12/3/2013
 * Lane.java 
 * creates lane for airplanes to land and depart
 */

import javafoundations.*;

public class Lane{
  
  private boolean occupied; //keeps track of whether an airplane is using this lane
  private String size; //size of the lane 
  private Queue<Airplane> lane; 
  
  public Lane(String s) {
    occupied = false;
    size = s;
    lane = new LinkedQueue<Airplane>();
  }
  
  public boolean checkLane() {
    return occupied;
  }
  
  public void useLane(Airplane name) {
    occupied = true;
    lane.enqueue(name);
  }
  
  public void clearlane () {
    occupied = false;
    lane.dequeue();
  }
  
  public Queue<Airplane> getLane() {
    return lane;
  }
  
}