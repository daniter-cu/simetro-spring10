package SIMBack;import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;public class tutorial { static ArrayList<Station> stationList=new ArrayList<Station>();static ArrayList<Line> lineList=new ArrayList<Line>();static ArrayList<Population> populationList=new ArrayList<Population>();static ArrayList<PopItem> popItemList=new ArrayList<PopItem>();static HashMap<String, Station> stationMap=new HashMap<String, Station>();static ArrayList<Station> tempList_stations=new ArrayList<Station>();static TimeLine tl=new TimeLine();public static void main (String[] args)  {  

//Order of coordinates and population does not matter because the keywords //are being provided.

Station A = new Station("A", new Coordinate(1, 5));
    stationList.add(A);
    //stationList.add(new Station("A", new Coordinate(1, 5)));
    stationMap.put("A", stationList.get(stationList.size() - 1)); 

Station B = new Station("B", new Coordinate(5, 4));
    stationList.add(B);
    //stationList.add(new Station("B", new Coordinate(5, 4)));
    stationMap.put("B", stationList.get(stationList.size() - 1)); 

for (Object a : new ArrayList<Object>( Arrays.asList(new String("[(B.10)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(a.toString().split("\\.")[0])), Double.parseDouble(new String(a.toString().split("\\.")[1]))));
    }
    stationMap.get("A").setPop(new Population(popItemList));
     

for (Object aa : new ArrayList<Object>( Arrays.asList(new String("[(A.5)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(aa.toString().split("\\.")[0])), Double.parseDouble(new String(aa.toString().split("\\.")[1]))));
    }
    stationMap.get("B").setPop(new Population(popItemList));
     

//Note that LINES may or may not be included in this file 

//Note that this is a circular line that goes from A to B and back

//If it is defined as (A,B) then there will be trains going from A to B //and from B to A

//In this case, a single train will go from A to B and back to A before //its route is done. 

    for (Object aaa : new ArrayList<Object>( Arrays.asList(new String("(A, B, A)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaa));
    }
    //lineList.add(new Line("t", 0.2, 100, 0.05,new ArrayList<Station>(tempList_stations)));
    Line t = new Line("t", 0.2, 0.05, 100,new ArrayList<Station>(tempList_stations));
    lineList.add(t);
    lineList.get(lineList.size() - 1).setRvsLine();
    tempList_stations.clear();  
 


//This is a user defined stat.

//It takes one argument of type time. 

 
 
 

//The below time intervals can be used to modify the simulation.

//Time intervals not included here will just continue to simulate with default values. 
int lateNight[] = {0,6};

int morningRush[] = {6,10};

int day[] = {10,15}; 
 


        Simulate sim=new Simulate();
        //TimeLine tl=new TimeLine();
        
        sim.createRoutingTables(stationList,lineList);
        
        
        for(int time_iter=0; time_iter < 24 ;time_iter++){
                        System.out.println("\n*********************************At time "+time_iter+"***********************************");
                        sim.peopleArrive(stationList, time_iter, tl);
                        sim.trainArrive(lineList, time_iter);
                        sim.trainMove(time_iter,tl);
                        
                     /*   System.out.println("------------------------------------STATION INFOMATION---------------------------------");
                        for(Station aStation : stationList)
                        {
                               System.out.println("Number of persons in "+aStation.getName()+": "+aStation.getCrowd().size());
                        }
                        System.out.println("--------------------------------------------END----------------------------------------");
*/


           { //this is to simulate a run where there are 24 units of time 

           //changeRate(Time, sourceStation, DestinationStation, newRate);

           //changes rate of people going from A to B during latenight from 10 to 1.


                   if(time_iter == lateNight[0])
                     sim.changeRate(A, B, 1);
                    
           //change*(time, line, newvalue);


                   if(time_iter == day[0])
                     sim.changeFrequency(t, 4);
                    
            

           }
        }
        /*
        int count=1;
        for (ArrayList<Train> alt : tl.getAllTrains()) {
            for (Train train : alt)
            {
                System.out.print("The Coordinate for the Train on "+train.getLine().getName()+" in time"+count+": ");
                train.getCoordinate().printCoor();
                System.out.println();
            }
            count++;
        }
    */
       // ShowGui sg=new ShowGui();
        //sg.Show(stationList, lineList,tl.getAllTrains());
        
        
        
       
         
 
//Allow user to decide if they want to see the gui or not.


        ShowGui sg=new ShowGui();
        sg.Show(stationList, lineList,tl.getAllTrains());
         //Generates gui simulation 
double val = pplTimesTwo(day, B); 
//this will print "This is twice the number of people waiting at Station A at time 10 to time 15: 20"

//print "This is twice the number of people waiting at station A at time " day.start "to time " day.end  ": " val;
 
}
public static double pplTimesTwo( int[] t, Station A )
{
double ppl = (tl.getNumWaiting(t, A) * 2);

return ppl;
}
 }