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
    	persons.add(person);
    	
    }
    
    public ArrayList<Person> getPersons(int index){
    	return allPersons.get(index);
    	
    }
    
    public void addAllPersons(){
    	allPersons.add((ArrayList<Person>)persons.clone());
 
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
    public double getFrequency(int t, Line l){
    	double lineFreq = 0;
    	if (t <= allLines.size()){
    		
    		ArrayList<Line> listLines = allLines.get(t);
    	
    		
    		//iterate through lines
    	
    		for (Line aLine: listLines){
    			//see if this is line you're looking for
    			if (aLine.getName().equals(l.getName())){
    				lineFreq = aLine.getRate();
    				//System.out.println("Line " + l.getName() + " frequency at time " + t + ": " + lineFreq);
    				break;
    				
    			}
    		
    			
    		}
    	
    	}
    	else
    	{
    		//System.out.println("Invalid time entered for getFrequency");
    	}
    	return lineFreq;
    }
    
    //get capacity for line at time t
    public double getCapacity(int t, Line l){
    	double lineCap = 0;
    	if (t <= allLines.size()){
    		
    		ArrayList<Line> listLines = allLines.get(t);
    	
    		
    		//iterate through lines
    	
    		for (Line aLine: listLines){
    			//see if this is line you're looking for
    			if (aLine.getName().equals(l.getName())){
    				lineCap = aLine.getCapacity();
    				//System.out.println("Line " + l.getName() + " capacity at time " + t + ": " + lineCap);
    				break;
    				
    			}
    		
    			
    		}
    	
    	}
    	else
    	{
    		//System.out.println("Invalid time entered for getCapacity");
    	}
    	return lineCap;
    }
    
    //get capacity for line at time t
    public double getSpeed(int t, Line l){
    	double lineSpeed = 0;
    	if (t <= allLines.size()){
    		
    		ArrayList<Line> listLines = allLines.get(t);
    	
    		
    		//iterate through lines
    	
    		for (Line aLine: listLines){
    			//see if this is line you're looking for
    			if (aLine.getName().equals(l.getName())){
    				lineSpeed = aLine.getSpeed();
    				//System.out.println("Line " + l.getName() + " speed at time " + t + ": " + lineSpeed);
    				break;
    				
    			}
    		
    			
    		}
    	
    	}
    	else
    	{
    		//System.out.println("Invalid time entered for getCapacity");
    	}
    	return lineSpeed;
    }
    
    public int getAverageWaitTime(int time){
		if(time<allPersons.size()){
		ArrayList<Person> ps=allPersons.get(time);
		int numOfPerson=ps.size();
		int totalWait=0;
		for (Person aPerson : ps)
		{
			totalWait+=aPerson.getTotalWaitTime();
		}
		int aw=totalWait/numOfPerson;
		return aw;
		}
		else return -1;
}

}
