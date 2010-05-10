package SIMBack;
import java.util.ArrayList;




public class main {
	
	static ArrayList<Station> stationList=new ArrayList<Station>();
	static ArrayList<Line> lineList=new ArrayList<Line>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		Station SA=new Station("110ST", new Coordinate(0,0));
		stationList.add(SA);
		Station SB=new Station("116ST", new Coordinate(0,6));
		stationList.add(SB);
		Station SC=new Station("125ST", new Coordinate(0,15));
		stationList.add(SC);
		Station SD=new Station("5AV", new Coordinate(10,6));
		stationList.add(SD);
		Station SE=new Station("4AV", new Coordinate(15,6));
		stationList.add(SE);

		System.out.println();
		

//		Station arr[]=new Station[3];
		ArrayList<Station> al=new ArrayList<Station>();
		al.add(SA);
		al.add(SB);
		al.add(SC);
		Line LA= new Line("Line1", 0.005, 0.05, 100,al );
                LA.setRvsLine();
		lineList.add(LA);
		
//		Station arr2[]=new Station[3];
		ArrayList<Station> al2=new ArrayList<Station>();
		al2.add(SB);
		al2.add(SD);
		al2.add(SE);
		Line LB=new Line("Line2",0.005,0.05,100,al2);
                LB.setRvsLine();
		lineList.add(LB);
		
//		Station arr3[]=new Station[3];
		ArrayList<Station> al3=new ArrayList<Station>();
		al3.add(SA);
		al3.add(SE);
		al3.add(SC);
		Line LC=new Line("Line3",0.01,0.15,200,al3);
                LC.setRvsLine();
		lineList.add(LC);

		
		PopItem PIA=new PopItem (SD,5);
		ArrayList<PopItem> PIArr=new ArrayList<PopItem>();
		PIArr.add(PIA);
		Population PA=new Population(PIArr);
		System.out.println("Population created");
		System.out.println();
		
		SA.setPop(PA);
		System.out.println("Population linked");
		System.out.println("For station: "+SA.getName()
				+" Dest: "+SA.getPop().getPopItemArr().get(0).getDest().getName()
				+" The rate is: "+SA.getPop().getPopItemArr().get(0).getRate());
		System.out.println();
		Simulate sim=new Simulate();
		TimeLine tl=new TimeLine();

		sim.createRoutingTables(stationList,lineList);
         
		for(int time=0;time<600;time++){
                        System.out.println("\n*********************************At time "+time+"***********************************");
                        sim.peopleArrive(stationList, time, tl);
                        sim.trainArrive(lineList, time);
                        sim.trainMove(time,tl);
                        tl.addStations(stationList);
                        tl.addLines(lineList);
                        System.out.println("------------------------------------STATION INFOMATION---------------------------------");
                        for(Station aStation : stationList)
                        {
                               System.out.println("Number of persons in "+aStation.getName()+": "+aStation.getCrowd().size());
                        }
                        System.out.println("--------------------------------------------END----------------------------------------");

		}
		

		
		int count=1;
		for (ArrayList<Train> alt : tl.getAllTrains()) {
			for (Train train : alt)
			{
				if((count>90)&&(count<110)) {
				System.out.print("The Coordinate for the Train on "+train.getLine().getName()+" in time"+count+": ");
				train.getCoordinate().printCoor();
				System.out.println();
				}
			}
			count++;
		}
		
		int[] timeArray = {10, 11, 12};
		tl.getFrequency(timeArray, LA);
		tl.getCapacity(timeArray, LC);
		System.out.println("new Frequency" + tl.getFrequency(timeArray, LC));
		
		System.out.println(SA);
		
		System.out.println(LA);
//		SA.changeRate(SB,0.2);
//		LA.changeCapacity(200);
//		LA.changeFrequency(0.02);
//		LA.changeSpeed(2);
		sim.changeRate(SA, SB, 0.2);
		sim.changeCapacity(LA, 200);
		sim.changeFrequency(LA, 0.02);
		sim.changeSpeed(LA, 2);
	
		System.out.println("frequency: " + tl.getFrequency(timeArray, LA));
		System.out.println("capacity: " + tl.getCapacity(timeArray, LA));
		System.out.println("speed: " + tl.getSpeed(timeArray, LA));
		
		System.out.println("The average watiting time is: "+tl.getAvgWaitTime(timeArray));
		System.out.println("The number of people waiting in Station "+SA.getName()+"at time "+10+" is: "+tl.getNumWaiting(timeArray, SA));
		System.out.println("The rate of population from SA to SD is: "+tl.getRate(timeArray, SA, SD));
		System.out.println("The number of people at time 10 is: "+tl.getNumPassengers(timeArray));
		ShowGui sg=new ShowGui();
		sg.Show(stationList, lineList,tl.getAllTrains());
	}
	
	
}
