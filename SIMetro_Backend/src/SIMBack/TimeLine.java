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
    public double getFrequency(int[] t, Line l){
    	
    	int firstTime = t[0];
    	
    	double lineFreq = 0;
    	if (allLines != null && firstTime <= allLines.size()){
    		
    		ArrayList<Line> listLines = allLines.get(firstTime);
    	
    		
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
    public double getCapacity(int[] t, Line l){
    	
    	int firstTime = t[0];
    	
    	double lineCap = 0;
    	if (allLines != null && firstTime <= allLines.size()){
    		
    		ArrayList<Line> listLines = allLines.get(firstTime);
    	
    		
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
    public double getSpeed(int[] t, Line l){
    	
    	//for testing purposes
    	//int test = allLines.size();
    	//System.out.println("size of line list is " + test);
    	
    	int firstTime = t[0];
    	System.out.println("first time is " + firstTime);
    	
    	double lineSpeed = 0;
    	
    	if (allLines != null && firstTime <= allLines.size()){
    		
    		ArrayList<Line> listLines = allLines.get(firstTime);
    	
    		
    		//iterate through lines
    	
    		for (Line aLine: listLines){
    			//see if this is line you're looking for
    			if (aLine.getName().equals(l.getName())){
    				lineSpeed = aLine.getSpeed();
    				
    				//left in for testing purposes
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
    
    public double getRate(int[] time, Station S1, Station S2){
    	
    	int firstTime = time[0];
    	
    	double rate=0;
    	if(allStations != null && firstTime<allStations.size()){
    		ArrayList<Station> st=allStations.get(firstTime);
    		for (Station aStation : st)
    		{
    			if (aStation.equals(S1))
    				for(PopItem pi : aStation.getPop().getPopItemArr())
    					if(pi.getDest().equals(S2))
    						rate=pi.getRate();
    		}
    		 return rate;
    		}
    	else return -1;
    	
    }
    
    public int getNumWaiting(int[] time, Station S){
    	
    	//for testing purposes
    	//int test = allStations.size();
    	//System.out.println("size of stations list is " + test);
    	
    	int num=0;
    	int firstTime = time[0];
    	if(allStations != null && firstTime<allStations.size()){
    		ArrayList<Station> st=allStations.get(firstTime);
    		for (Station aStation : st)
    		{
    			if (aStation.equals(S))
    				num=aStation.getCrowd().size();  			
    		}
    		 return num;
    		}
    	else return -1;
    }
    
    public int getAvgWaitTime(int[] time){
    	
    	
    	
    	int firstTime = time[0];
		if(allPersons != null && firstTime<allPersons.size()){
		ArrayList<Person> ps=allPersons.get(firstTime);
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
    public int getNumPassengers(int[] time){
    	int firstTime = time[0];
    	int num = 0;
    	if (allPersons != null && firstTime <= allPersons.size()){
    		
    		ArrayList<Person> ps = allPersons.get(firstTime);
    		num=ps.size();  	
    		return num;
    	}
    	else
    	{
    		//System.out.println("Invalid time entered for getFrequency");
    	}
    	return -1;
    	
    }

}
