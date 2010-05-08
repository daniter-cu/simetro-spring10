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
		

		Station arr[]=new Station[3];
		ArrayList<Station> al=new ArrayList<Station>();
		al.add(SA);
		al.add(SB);
		al.add(SC);
		Line LA= new Line("Line1", 0.2, 2.0, 100,al );
                LA.setRvsLine();
		lineList.add(LA);
		
		Station arr2[]=new Station[3];
		ArrayList<Station> al2=new ArrayList<Station>();
		al2.add(SB);
		al2.add(SD);
		al2.add(SE);
		Line LB=new Line("Line2",0.2,2.0,100,al2);
                LB.setRvsLine();
		lineList.add(LB);
		
		Station arr3[]=new Station[3];
		ArrayList<Station> al3=new ArrayList<Station>();
		al3.add(SA);
		al3.add(SE);
		al3.add(SC);
		Line LC=new Line("Line3",0.2,3.0,200,al3);
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

		sim.createRoutingTables(stationList,lineList);
                //sim.createTimeLine(60);
		for(int time=0;time<12;time++){
                        System.out.println("\n*********************************At time "+time+"***********************************");
			sim.peopleArrive(stationList, time);
			sim.trainArrive(lineList, time);
                        sim.trainMove(time);

                        System.out.println("------------------------------------STATION INFOMATION---------------------------------");
                        for(Station aStation : stationList)
                        {
                               System.out.println("Number of persons in "+aStation.getName()+": "+aStation.getCrowd().size());
                        }
                        System.out.println("--------------------------------------------END----------------------------------------");

		}
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new SIMGUI().setVisible(true);
            }
        });
	}
}