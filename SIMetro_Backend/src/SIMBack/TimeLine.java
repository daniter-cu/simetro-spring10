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
   
}
