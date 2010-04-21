
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Station SA=new Station("110ST", new Coordinate(0,0));
		Station SB=new Station("116ST", new Coordinate(0,6));
		System.out.println("Stations created:");
		System.out.println(SA.getName());
		System.out.println(SB.getName());
		System.out.println();
		
		Station arr[]=new Station[2];
		arr[0]=SA;
		arr[1]=SB;
		Line LA= new Line("Line1", 0.2, 2.0, 100,arr );
		System.out.println("Line created:");
		System.out.println(LA.getName());
		System.out.println();
		
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
		
		sim.createRoutingTables();
		
		for(int time=0;time<60;time++){
			sim.peopleArrive();
			sim.trainArrive();
			sim.trainMove();
		}
		


	}

}
