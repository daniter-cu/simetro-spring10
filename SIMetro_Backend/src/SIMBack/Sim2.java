package SIMBack;import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;public class Sim2 { static ArrayList<Station> stationList=new ArrayList<Station>();static ArrayList<Line> lineList=new ArrayList<Line>();static ArrayList<Population> populationList=new ArrayList<Population>();static ArrayList<PopItem> popItemList=new ArrayList<PopItem>();static HashMap<String, Station> stationMap=new HashMap<String, Station>();static ArrayList<Station> tempList_stations=new ArrayList<Station>();static TimeLine tl=new TimeLine();public static void main (String[] args)  {  

//set station coordinates and populations
Station s_110ST = new Station("s_110ST", new Coordinate(0, 0));
    stationList.add(s_110ST);
    //stationList.add(new Station("s_110ST", new Coordinate(0, 0)));
    stationMap.put("s_110ST", stationList.get(stationList.size() - 1));


Station s_116ST = new Station("s_116ST", new Coordinate(0, 6));
    stationList.add(s_116ST);
    //stationList.add(new Station("s_116ST", new Coordinate(0, 6)));
    stationMap.put("s_116ST", stationList.get(stationList.size() - 1));



Station s_125ST = new Station("s_125ST", new Coordinate(0, 15));
    stationList.add(s_125ST);
    //stationList.add(new Station("s_125ST", new Coordinate(0, 15)));
    stationMap.put("s_125ST", stationList.get(stationList.size() - 1));


Station s_5AV = new Station("s_5AV", new Coordinate(10, 6));
    stationList.add(s_5AV);
    //stationList.add(new Station("s_5AV", new Coordinate(10, 6)));
    stationMap.put("s_5AV", stationList.get(stationList.size() - 1));


Station s_4AV = new Station("s_4AV", new Coordinate(15, 6));
    stationList.add(s_4AV);
    //stationList.add(new Station("s_4AV", new Coordinate(15, 6)));
    stationMap.put("s_4AV", stationList.get(stationList.size() - 1));


//set populations
for (Object a : new ArrayList<Object>( Arrays.asList(new String("[(s_5AV.5)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(a.toString().split("\\.")[0])), Double.parseDouble(new String(a.toString().split("\\.")[1]))));
    }
    stationMap.get("s_110ST").setPop(new Population(popItemList));
    

for (Object aa : new ArrayList<Object>( Arrays.asList(new String("[(s_110ST.10)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(aa.toString().split("\\.")[0])), Double.parseDouble(new String(aa.toString().split("\\.")[1]))));
    }
    stationMap.get("s_125ST").setPop(new Population(popItemList));
    

for (Object aaa : new ArrayList<Object>( Arrays.asList(new String("[(s_4AV.3)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(aaa.toString().split("\\.")[0])), Double.parseDouble(new String(aaa.toString().split("\\.")[1]))));
    }
    stationMap.get("s_5AV").setPop(new Population(popItemList));
    

for (Object aaaa : new ArrayList<Object>( Arrays.asList(new String("[(s_125ST.10)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(aaaa.toString().split("\\.")[0])), Double.parseDouble(new String(aaaa.toString().split("\\.")[1]))));
    }
    stationMap.get("s_4AV").setPop(new Population(popItemList));
    

//construct lines 1, 2 and 3

    for (Object aaaaa : new ArrayList<Object>( Arrays.asList(new String("(s_110ST, s_116ST, s_125ST)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaaaa));
    }
    //lineList.add(new Line("Line1", 0.005, 100, 0.05,new ArrayList<Station>(tempList_stations)));
    Line Line1 = new Line("Line1", 0.005, 0.05, 100,new ArrayList<Station>(tempList_stations));
    lineList.add(Line1);
    lineList.get(lineList.size() - 1).setRvsLine();
    tempList_stations.clear(); 



    for (Object aaaaaa : new ArrayList<Object>( Arrays.asList(new String("(s_116ST, s_5AV, s_4AV)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaaaaa));
    }
    //lineList.add(new Line("Line2", 0.005, 100, 0.05,new ArrayList<Station>(tempList_stations)));
    Line Line2 = new Line("Line2", 0.005, 0.05, 100,new ArrayList<Station>(tempList_stations));
    lineList.add(Line2);
    lineList.get(lineList.size() - 1).setRvsLine();
    tempList_stations.clear();



    for (Object aaaaaaa : new ArrayList<Object>( Arrays.asList(new String("(s_110ST, s_4AV, s_125ST)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaaaaaa));
    }
    //lineList.add(new Line("Line3", 0.01, 200, 0.05,new ArrayList<Station>(tempList_stations)));
    Line Line3 = new Line("Line3", 0.01, 0.05, 200,new ArrayList<Station>(tempList_stations));
    lineList.add(Line3);
    lineList.get(lineList.size() - 1).setRvsLine();
    tempList_stations.clear();

//set time
int early[] = {1,6};
int late[] = {6,12};


//create stat that adds number of people waiting at 2 stations at time t


//run simulation

        Simulate sim=new Simulate();
        //TimeLine tl=new TimeLine();
        
        sim.createRoutingTables(stationList,lineList);
        
        
        for(int time_iter=0; time_iter < 100 ;time_iter++){
                        System.out.println("\n*********************************At time "+time_iter+"***********************************");
                        sim.peopleArrive(stationList, time_iter, tl);
                        sim.trainArrive(lineList, time_iter);
                        sim.trainMove(time_iter,tl);
                        tl.addStations(stationList);
                        tl.addLines(lineList);
                        
                     /*   System.out.println("------------------------------------STATION INFOMATION---------------------------------");
                        for(Station aStation : stationList)
                        {
                               System.out.println("Number of persons in "+aStation.getName()+": "+aStation.getCrowd().size());
                        }
                        System.out.println("--------------------------------------------END----------------------------------------");
*/


           {

           	//get speed of trains on line3 in early period; speed should be .05
           	double speed = tl.getSpeed(early, Line3);

           	System.out.println("Speed of trains on Line3 in the morning is "+speed+"");

           	
           	//modify speed
           	
                   if(time_iter == late[0])
                     sim.changeSpeed(Line3, 0.08);
                   

           	//get new speed
           	double newSpeed = tl.getSpeed(late, Line3);

           	System.out.println("Speed of trains on Line3 in the evening is "+newSpeed+"");
           	

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
        
        
        
       
        

double numberWaiting = combineNumWaiting(early, s_110ST, s_116ST);

System.out.println("Number of people waiting at stations 110 and 116 in the morning is "+numberWaiting+""); 


        ShowGui sg=new ShowGui();
        sg.Show(stationList, lineList,tl.getAllTrains());
        
}
public static double combineNumWaiting( int[] t, Station A, Station B )
{
double people1 = tl.getNumWaiting(t, A);
double people2 = tl.getNumWaiting(t, B);
double total = (people1 + people2);

return total;
}
 }