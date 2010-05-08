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
		

		System.out.println("start");
		
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
		ArrayList<Station> al=new ArrayList<Station>();
		al.add(SA);
		al.add(SB);
		al.add(SC);
		Line LA= new Line("Line1", 0.2, 2.0, 100,al );
		lineList.add(LA);
		
		Station arr2[]=new Station[3];
		ArrayList<Station> al2=new ArrayList<Station>();
		al2.add(SB);
		al2.add(SD);
		al2.add(SE);
		Line LB=new Line("Line2",0.2,2.0,100,al2);
		lineList.add(LB);
		
		Station arr3[]=new Station[3];
		ArrayList<Station> al3=new ArrayList<Station>();
		al3.add(SA);
		al3.add(SE);
		al3.add(SC);
		Line LC=new Line("Line3",0.2,3.0,200,al3);
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

		sim.createRoutingTables(stationList,lineList);
                //sim.createTimeLine(60);
		for(int time=0;time<60;time++){

			sim.peopleArrive(stationList, time);
			sim.trainArrive(lineList, time);
                        System.out.println("-------------At time "+time+"------------");
			sim.trainMove(time);
		}
		
		//for test purposes: get information about people and trains in the system at time 30
        sim.getPersonArray(30);
        
        sim.getTrainArray(30);
        
        
		ShowGui sg=new ShowGui();
		sg.Show();
		
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SIMGUI().setVisible(true);
//            }
//        });
        
        //for test purposes: get information about people and trains in the system at time 30
        //sim.getPersonArray(30);
        
        //sim.getTrainArray(30);
	}
}
