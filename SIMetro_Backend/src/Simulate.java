import java.util.ArrayList;




public class Simulate {
	public void createRoutingTables(ArrayList<Station> stationList,ArrayList<Line> lineList) {
		//for each station, create its routing table and link it to the station
		ArrayList<Edge> edgeList=new ArrayList<Edge>();
		for(int i=0;i<lineList.size();i++) {
			for(int j=0;j<lineList.get(i).getRoute().length-1;j++) {
				Station s1=lineList.get(i).getRoute()[j];
				Station s2=lineList.get(i).getRoute()[j+1];
				Edge edge=new Edge(s1,s2,s1.getCoordinate().getDistance(s2.getCoordinate()),(Math.round((s1.getCoordinate().getDistance(s2.getCoordinate())/lineList.get(i).getSpeed()*10.0)))/10.0,lineList.get(i));				
				edgeList.add(edge);
			}
		}
		System.out.println("edgeList.size() "+edgeList.size());
		
		for(int si=0;si<stationList.size();si++) {
			RoutingTab RoutingTable[]=new RoutingTab[stationList.size()-1];
			int sli=0;
			for(int rti=0;rti<stationList.size()-1;rti++) {
				if(sli==si)
					sli++;
					RoutingTab rt=new RoutingTab();
					rt.setDest(stationList.get(sli));
					rt.setDist(9999);
					RoutingTable[rti]=rt;
					sli++;

			}
			stationList.get(si).setRoutingTable(RoutingTable);
			
		}
		for(int ei=0;ei<edgeList.size();ei++) {
			Edge tempE=edgeList.get(ei);
			tempE.getS1().getRTabByDest(tempE.getS2()).setDist(tempE.getTimedist());
			tempE.getS1().getRTabByDest(tempE.getS2()).setLine(tempE.getLine());
			tempE.getS1().getRTabByDest(tempE.getS2()).setNext(tempE.getS2());
			tempE.getS2().getRTabByDest(tempE.getS1()).setDist(tempE.getTimedist());
			tempE.getS2().getRTabByDest(tempE.getS1()).setLine(tempE.getLine());
			tempE.getS2().getRTabByDest(tempE.getS1()).setNext(tempE.getS1());
			
		}
		
		System.out.println("Original Routing Table:");
		for(int i=0;i<stationList.size();i++) {
			System.out.println("RoutingTable for Station "+stationList.get(i).getName());
			for(int j=0;j<stationList.get(i).getRoutingTable().length;j++) {
				System.out.println("Dest: "+stationList.get(i).getRoutingTable()[j].getDest().getName()
						+"\tDist: "+stationList.get(i).getRoutingTable()[j].getDist()
						+"\tLine: "+stationList.get(i).getRoutingTable()[j].getLine().getName()
						+"\tNext: "+stationList.get(i).getRoutingTable()[j].getNext().getName());
			}
		}
//		System.out.println("===="+stationList.get(0).getRTabByDest(stationList.get(1)).getDist());
		System.out.println("Constructing...");
		for(int i=0;i<stationList.size()-1;i++) {
//			System.out.println("i:"+i);
			for(int si=0;si<stationList.size();si++) {
//				System.out.println("si:"+si);
				for(int rti=0;rti<stationList.get(si).getRoutingTable().length;rti++) {
//					System.out.println("rti:"+rti);
					Station source=stationList.get(si);
					Station dest=stationList.get(si).getRoutingTable()[rti].getDest();
					for(int tr=0;tr<stationList.size()-1;tr++) {
						if((!source.getRoutingTable()[tr].equals(dest))&&(!source.getRoutingTable()[tr].getDest().equals(dest))) {
//							System.out.println(source.getRoutingTable()[tr].getDist());
//							System.out.println("+++++"+source.getRoutingTable()[tr].getDest().getName());
//							System.out.println(dest.getName());
//							System.out.println(source.getRoutingTable()[tr].getDest().getRTabByDest(dest).getDist());

							double newdist=source.getRoutingTable()[tr].getDist()+source.getRoutingTable()[tr].getDest().getRTabByDest(dest).getDist();
							if (newdist<source.getRoutingTable()[rti].getDist()) {
								source.getRoutingTable()[rti].setDist(newdist);
								source.getRoutingTable()[rti].setLine(source.getRoutingTable()[tr].getLine());
								source.getRoutingTable()[rti].setNext(source.getRoutingTable()[tr].getNext());
							}
						}
					}
				}
			}
			
			
		}
		System.out.println();
		System.out.println("Routing Table all constructed!");
		for(int i=0;i<stationList.size();i++) {
			System.out.println("RoutingTable for Station "+stationList.get(i).getName());
			for(int j=0;j<stationList.get(i).getRoutingTable().length;j++) {
				System.out.println("Dest: "+stationList.get(i).getRoutingTable()[j].getDest().getName()
						+"\tDist: "+stationList.get(i).getRoutingTable()[j].getDist()
						+"\tLine: "+stationList.get(i).getRoutingTable()[j].getLine().getName()
						+"\tNext: "+stationList.get(i).getRoutingTable()[j].getNext().getName());
			}
		}
		
		
	}
	
	
	public void peopleArrive(ArrayList<Station> stationList, int arriveTime){
		//look up in the population objects to see if new people should be created to arrive at each station
		//for each new person, create him using	public Person(int arrivalTime, Station dest, Station source, Station next)
		//by also looking up the routing table
		
		//iterate through station list
		for(int si=0;si<stationList.size();si++) {
			
			//get population for station
			Population aPop = stationList.get(si).getPop();
			
			//get pop item array associated with population
			PopItem[] popItemArray = aPop.getPopItemArr();
			
			//iterate through pop item array
			for (PopItem anItem : popItemArray){
				
				//get destination and rate 
				Station destStation = anItem.getDest();
				int popRate = (int)anItem.getRate();
			
				//create a new person up to the rate value
				for (int i = 0; i < popRate; i++){
					//arrivalTime = arriveTime, StationDest = destStation, 
					//StationSource = stationList.get(si), Station next = get from routing table
					
					Station sourceStation = stationList.get(si);
			
					RoutingTab[] aTable = sourceStation.getRoutingTable();
					
					Station nextStation = aTable.getNext();
					
					//create new person
					Person aPerson = new Person(arriveTime, destStation, sourceStation, nextStation);
					
					//should we add each person to an array or something?
				}
			}
			
		}
	}
	
	
	public void trainArrive(){
		//look up in the line objects to see if new trains should be created to arrive at each station 
		//using public Train(Line line, int arrivalTime, Coordinate coordinate,int capacity, double speed)
	}
	
	public void trainMove() {
		//update the coordinates of each train running on lines
		//if the train arrives at a station, unload the people that wants to get off this train, check if these people has already reached their destination (add the departure time), 
		//                                                                                             or do they need to transfer to another line (add new arrival time using public void setTransferTime(ArrayList<TimePair> transferTime))
		//                                   load the people that are waiting to get on this train
	}
	
	
	

}
