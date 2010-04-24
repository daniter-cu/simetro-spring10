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
		

		Station arr[]=new Station[3];
		arr[0]=SA;
		arr[1]=SB;
		arr[2]=SC;
		Line LA= new Line("Line1", 0.2, 2.0, 100,arr );
		lineList.add(LA);
		
		Station arr2[]=new Station[3];
		arr2[0]=SB;
		arr2[1]=SD;
		arr2[2]=SE;
		Line LB=new Line("Line2",0.2,2.0,100,arr2);
		lineList.add(LB);
		
		Station arr3[]=new Station[3];
		arr3[0]=SA;
		arr3[1]=SE;
		arr3[2]=SC;
		Line LC=new Line("Line3",0.2,3.0,200,arr3);
		lineList.add(LC);

		
		PopItem PIA=new PopItem (SA,5);
		PopItem PIArr[]=new PopItem[1];
		PIArr[0]=PIA;
		Population PA=new Population(PIArr);
		System.out.println("Population created");
		System.out.println();
		
		SA.setPop(PA);
		System.out.println("Population linked");
		System.out.println("For station: "+SA.getName()
				+" Dest: "+SA.getPop().getPopItemArr()[0].getDest().getName()
				+" The rate is: "+SA.getPop().getPopItemArr()[0].getRate());
		System.out.println();
		Simulate sim=new Simulate();
		
		sim.createRoutingTables(stationList,lineList);
		
		for(int time=0;time<60;time++){
			sim.peopleArrive();
			sim.trainArrive();
			sim.trainMove();
		}
		


	}

}
