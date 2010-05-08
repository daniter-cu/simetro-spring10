import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;public class smalltown { static ArrayList<Station> stationList=new ArrayList<Station>();static ArrayList<Line> lineList=new ArrayList<Line>();static ArrayList<Population> populationList=new ArrayList<Population>();static ArrayList<PopItem> popItemList=new ArrayList<PopItem>();static HashMap<String, Station> stationMap=new HashMap<String, Station>();static ArrayList<Station> tempList_stations=new ArrayList<Station>();public static void main (String[] args)  {  

stationList.add(new Station("s_110ST", new Coordinate(0, 0)));
    stationMap.put("s_110ST", stationList.get(stationList.size() - 1));

stationList.add(new Station("s_116ST", new Coordinate(0, 6)));
    stationMap.put("s_116ST", stationList.get(stationList.size() - 1));

stationList.add(new Station("s_125ST", new Coordinate(0, 15)));
    stationMap.put("s_125ST", stationList.get(stationList.size() - 1));

stationList.add(new Station("s_5AV", new Coordinate(10, 6)));
    stationMap.put("s_5AV", stationList.get(stationList.size() - 1));

stationList.add(new Station("s_4AV", new Coordinate(15, 6)));
    stationMap.put("s_4AV", stationList.get(stationList.size() - 1));

for (Object a : new ArrayList<Object>( Arrays.asList(new String("[(s_116ST.5)]").replaceAll("\\s+|\\(|\\)|\\[|\\]", "").split(",")))) {
        popItemList.add(new PopItem(stationMap.get(new String(a.toString().split("\\.")[0])), Double.parseDouble(new String(a.toString().split("\\.")[1]))));
    }
    stationMap.get("s_110ST").setPop(new Population(popItemList));
    




    for (Object aa : new ArrayList<Object>( Arrays.asList(new String("(s_110ST, s_116ST, s_125ST)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aa));
    }
    lineList.add(new Line("Line1", 0.2, 2, 100,new ArrayList<Station>(tempList_stations)));
    tempList_stations.clear(); 


    for (Object aaa : new ArrayList<Object>( Arrays.asList(new String("(s_116ST, s_5AV, s_4AV)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaa));
    }
    lineList.add(new Line("Line2", 0.2, 2, 100,new ArrayList<Station>(tempList_stations)));
    tempList_stations.clear();
	

    for (Object aaaa : new ArrayList<Object>( Arrays.asList(new String("(s_110ST, s_4AV, s_125ST)").replaceAll("\\s+|\\(|\\)", "").split(",")))) {
        tempList_stations.add(stationMap.get((String)aaaa));
    }
    lineList.add(new Line("Line3", 0.2, 3, 100,new ArrayList<Station>(tempList_stations)));
    tempList_stations.clear();


        Simulate sim=new Simulate();
        sim.createRoutingTables(stationList,lineList);
        {
        System.out.println("Done");;
        }
        


double lambda = getRate(A, 5, Gabda);

double y = (1 * 2);
double z = (3.23 - 0);

int rushhour[2] = {0,10};

if( (z > 4)){
	y = 5;}
else
{
y = -4;
}



double a = 1;



String Hello = "Test";		 //problems here - loses quotes

int rushhour[2] = {0,10};

if ((x > y)) {
double x = 1;
} 
else {
double z = getFrequency(B);
}

if ((x > y)) {
double x = 1;
} 



//ShowGUI();	 //keep outside simulate block




}
public static int statA( int b, num a )
{
double z;
double a = 6;
double b = 6;
a = (a + b);
double c = getRate(A);

return a;
}
 }