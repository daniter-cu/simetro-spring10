/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author QB
 */
package SIMBack;
import java.util.ArrayList;

public class TimeLine {
    private ArrayList<Train> trains=new ArrayList<Train>();
    private ArrayList<ArrayList<Train>> allTrains=new ArrayList<ArrayList<Train>>();
    private ArrayList<Person> persons = new ArrayList<Person>();
    private ArrayList<ArrayList<Person>> allPersons = new ArrayList<ArrayList<Person>>();
    private ArrayList<Station> stations= new ArrayList<Station>();
    private ArrayList<ArrayList<Station>> allStations=new ArrayList<ArrayList<Station>>();
    private ArrayList<Line> lines=new ArrayList<Line>();
    private ArrayList<ArrayList<Line>> allLines=new ArrayList<ArrayList<Line>>();
    
    public void addTrain(Train train){
    	trains.add(train.clone());   	
    }
    
    public ArrayList<Train> getTrains(int index){    	
    	return allTrains.get(index);
    }
    
    
    public void addAllTrains(){
    	
    	allTrains.add(trains);
    	trains=new ArrayList<Train>();
    }
    
    public ArrayList<ArrayList<Train>> getAllTrains(){
    	return allTrains;
    	
    }
    
    public void addPerson(Person person){
    	persons.add(person.clone());
    	
    }
    
    public ArrayList<Person> getPersons(int index){
    	return allPersons.get(index);
    	
    }
    
    public void addAllPersons(){
    	allPersons.add(persons);
    	persons = new ArrayList<Person>();
    }
    
    public ArrayList<ArrayList<Person>> getAllPersons(){
    	return allPersons;
    }
    
    public void addStations(ArrayList<Station> stations){
    	this.allStations.add((ArrayList<Station>)stations.clone());
    }
    
    public void addLines(ArrayList<Line> lines){
    	this.allLines.add((ArrayList<Line>)lines.clone());
    }
    
    public void getAverageTime(){
    	    	
    }

    //get frequency between trains on line l, at time t
    public void getFrequency(int t, Line l){
    	
    	if (t <= allLines.size()){
    		
    		ArrayList<Line> listLines = allLines.get(t);
    	
    		double lineFreq = 0;
    		//iterate through lines
    	
    		for (Line aLine: listLines){
    			//see if this is line you're looking for
    			if (aLine.getName().equals(l.getName())){
    				lineFreq = aLine.getRate();
    				System.out.println("Line " + l.getName() + " frequency at time " + t + ": " + lineFreq);
    				break;
    				
    			}
    		
    			
    		}
    	
    	}
    	else
    	{
    		System.out.println("Invalid time entered for getFrequency");
    	}
    }
    
    //public void getCapacity(int t, Line l)
   
}
