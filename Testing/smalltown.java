package SIMBack;import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;public class smalltown { static ArrayList<Station> stationList=new ArrayList<Station>();static ArrayList<Line> lineList=new ArrayList<Line>();static ArrayList<Population> populationList=new ArrayList<Population>();static ArrayList<PopItem> popItemList=new ArrayList<PopItem>();static HashMap<String, Station> stationMap=new HashMap<String, Station>();static ArrayList<Station> tempList_stations=new ArrayList<Station>();public static void main (String[] args)  {  Population p1 {

(B, 10) //destination station, people per unit time

} 

Population p2 {

(A, 5)

} 

//Order of coordinates and population does not matter because the keywords //are being provided.

Station A {

Coordinates (1,5); //coordinates

Population (p1); //associate p1 with this station

} 

Station B {

Coordinates (5,4);

Population (p2);

} 

//Note that LINES may or may not be included in this file 

//Note that this is a circular line that goes from A to B and back

//If it is defined as (A,B) then there will be trains going from A to B //and from B to A

//In this case, a single train will go from A to B and back to A before //its route is done. 
Line t {

Stations(A, B, A);

Frequency(0.2);

Capacity (100);

Speed (0.05);

}  

 
 

//This is a user defined stat.

//It takes one argument of type time. 

Stat pplTimesTwo(Time t, Station A)

{

      num ppl = getNumWaiting(t, A) * 2;

      return ppl;

} 
 


//The below time intervals can be used to modify the simulation.

//Time intervals not included here will just continue to simulate with default values. 
Time lateNight [0, 6];

Time morningRush [6, 10];

Time day [10, 15]; 
 

Simulate (24) { //this is to simulate a run where there are 24 units of time 

//changeRate(Time, sourceStation, DestinationStation, newRate);

//changes rate of people going from A to B during latenight from 10 to 1.

changeRate(latenight, A, B, 1); 
//change*(time, line, newvalue);

changeFrequency(day, t, 4); 
 

} 
 
//Allow user to decide if they want to see the gui or not.

showGUI(); //Generates gui simulation 
num val = pplTimesTwo(day); 
//this will print "This is twice the number of people waiting at Station A at time 10 to time 15: 20"

print This is twice the number of people waiting at station A at time  daystart to time  dayend  :  val;
}
 }