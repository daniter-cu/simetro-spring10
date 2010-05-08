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
    static private int timeRange;
    static ArrayList[] table;
    
    //private ArrayList<Person> persons=new ArrayList<Person>();
    //private ArrayList<Train> trains=new ArrayList<Train>();
    private ArrayList<ArrayList> personList = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> trainList = new ArrayList<ArrayList>();
    
    public TimeLine(){
    	
    }
    
    public TimeLine(int timeRange){
        table=new ArrayList[timeRange];

    }
    
    //time may be a point or a range
    public void setTime(int time){
    	table = new ArrayList[time];
    	
    }
    public static int getTimeRange(){
            return timeRange;
        }
    
    public void setPersons(ArrayList<Person> persons){
    	
    	personList.add(persons);
    	System.out.println("Person array list has been added");
    	
    	//System.out.println("Size of list is " + personList.size());
    }


	public void setTrains(ArrayList<Train> trains){
		
		trainList.add(trains);
		System.out.println("Train array list has been added");
		
	}
	
	public ArrayList<ArrayList> getPersons(int time){
		
		return personList;
	}
	                      
	public ArrayList<ArrayList> getTrains(int time){
		
		return trainList;
	}
}