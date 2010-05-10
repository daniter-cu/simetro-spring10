package SIMBack;import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;public class test { static ArrayList<Station> stationList=new ArrayList<Station>();static ArrayList<Line> lineList=new ArrayList<Line>();static ArrayList<Population> populationList=new ArrayList<Population>();static ArrayList<PopItem> popItemList=new ArrayList<PopItem>();static HashMap<String, Station> stationMap=new HashMap<String, Station>();static ArrayList<Station> tempList_stations=new ArrayList<Station>();static TimeLine tl=new TimeLine();public static void main (String[] args)  {  // 3 stations S1,S2,S3 in a triangle with lines between each pair of them with identical capacity, speed and frequency

// I am willing to double the frequency of one of the lines. Which line should I choose: Use the average number of waiting people per station as the performance criteria

// for each Line in Lines
	// getFrequency(Line)
	// double the Frequency of Line
	// run simulation
	// Find the new avg. number of waiting people per station in the system
	// Restore the originial frquency of Line

// Display the statistics map in increasing order of OverallAvgWaitNumber: (LineWithIncreasedFrequency, OverallAvgWaitNumber)


// -------------------------------------------

//set station coordinates and populations
Station S1 = new Station("S1", new Coordinate(0, 0));
    stationList.add(S1);
    //stationList.add(new Station("S1", new Coordinate(0, 0)));
    stationMap.put("S1", stationList.get(stationList.size() - 1));


Station S2 = new Station("S2", new Coordinate(3, 4));
    stationList.add(S2);
    //stationList.add(new Station("S2", new Coordinate(3, 4)));
    stationMap.put("S2", stationList.get(stationList.size() - 1));

Station S3 = new Station("S3", new Coordinate(6, 0));
    stationList.add(S3);
    //stationList.add(new Station("S3", new Coordinate(6, 0)));
    stationMap.put("S3", stationList.get(stationList.size() - 1));


for (Object a : new ArrayList<Object>( Arrays.asList(new String("[(S2.10), (S3.20)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(a.toString().split("\\.")[0])), Double.parseDouble(new String(a.toString().split("\\.")[1]))));
    }
    stationMap.get("S1").setPop(new Population(popItemList));
    

for (Object aa : new ArrayList<Object>( Arrays.asList(new String("[(S3.35), (S1.10)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(aa.toString().split("\\.")[0])), Double.parseDouble(new String(aa.toString().split("\\.")[1]))));
    }
    stationMap.get("S2").setPop(new Population(popItemList));
    

for (Object aaa : new ArrayList<Object>( Arrays.asList(new String("[(S1.20)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(aaa.toString().split("\\.")[0])), Double.parseDouble(new String(aaa.toString().split("\\.")[1]))));
    }
    stationMap.get("S3").setPop(new Population(popItemList));
    


//construct lines 1, 2 and 3

    for (Object aaaa : new ArrayList<Object>( Arrays.asList(new String("(S1, S2, S1)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaaa));
    }
    //lineList.add(new Line("Line1", 0.005, 100, 0.05,new ArrayList<Station>(tempList_stations)));
    Line Line1 = new Line("Line1", 0.005, 0.05, 100,new ArrayList<Station>(tempList_stations));
    lineList.add(Line1);
    lineList.get(lineList.size() - 1).setRvsLine();
    tempList_stations.clear();


    for (Object aaaaa : new ArrayList<Object>( Arrays.asList(new String("(S2, S3, S2)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaaaa));
    }
    //lineList.add(new Line("Line2", 0.005, 100, 0.05,new ArrayList<Station>(tempList_stations)));
    Line Line2 = new Line("Line2", 0.005, 0.05, 100,new ArrayList<Station>(tempList_stations));
    lineList.add(Line2);
    lineList.get(lineList.size() - 1).setRvsLine();
    tempList_stations.clear();


    for (Object aaaaaa : new ArrayList<Object>( Arrays.asList(new String("(S3, S1, S3)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaaaaa));
    }
    //lineList.add(new Line("Line3", 0.005, 100, 0.05,new ArrayList<Station>(tempList_stations)));
    Line Line3 = new Line("Line3", 0.005, 0.05, 100,new ArrayList<Station>(tempList_stations));
    lineList.add(Line3);
    lineList.get(lineList.size() - 1).setRvsLine();
    tempList_stations.clear();


//set time
int early[] = {1,6};




//run simulation

        Simulate sim=new Simulate();
        //TimeLine tl=new TimeLine();
        
        sim.createRoutingTables(stationList,lineList);
        
        
        for(int time_iter=0; time_iter < 100 ;time_iter++){
                        System.out.println("\n*********************************At time "+time_iter+"***********************************");
                        sim.peopleArrive(stationList, time_iter, tl);
                        sim.trainArrive(lineList, time_iter);
                        sim.trainMove(time_iter,tl);                        tl.addStations(stationList);                        tl.addLines(lineList);
                        
                     /*   System.out.println("------------------------------------STATION INFOMATION---------------------------------");
                        for(Station aStation : stationList)
                        {
                               System.out.println("Number of persons in "+aStation.getName()+": "+aStation.getCrowd().size());
                        }
                        System.out.println("--------------------------------------------END----------------------------------------");
*/


           {

           	//get frequency of train on line1 in early period
           	double freq = tl.getFrequency(early, Line1);

           	//modify frequency
           	double chan = (freq * 2);
           	
                   if(time_iter == early[0])
                     sim.changeFrequency(Line1, chan);
                   

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
        
        
        
       
        

double numberWaiting = AvgNumWaiting(early, S1, S2, S3);

System.out.println("Average Number of people waiting per station in the morning is "+numberWaiting+""); 


        ShowGui sg=new ShowGui();
        sg.Show(stationList, lineList,tl.getAllTrains());
        
}
public static double AvgNumWaiting( int[] t, Station A, Station B, Station C )
{
double people1 = tl.getNumWaiting(t, A);
double people2 = tl.getNumWaiting(t, B);
double people3 = tl.getNumWaiting(t, C);
double total = ((people1 + people2) + people3);
double ret = (total / 3);

return ret;
}
 }