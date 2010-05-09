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
    public void addStaions(ArrayList<Station> stations){
    	this.allStations.add(stations);
    }
    public void addLines(ArrayList<Line> lines){
    	this.allLines.add(lines);
    }   

   
}
