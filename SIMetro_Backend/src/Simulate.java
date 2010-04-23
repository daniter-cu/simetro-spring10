import java.util.ArrayList;




public class Simulate {
	public void createRoutingTables(Station[] StationArr,Line[] LineArr) {
		//for each station, create its routing table and link it to the station
		ArrayList<Edge> edgeList=new ArrayList();
		for(int i=0;i<LineArr.length;i++) {
			for(int j=0;j<LineArr[i].getRoute().length-1;j++) {
				Station s1=LineArr[i].getRoute()[j];
				Station s2=LineArr[i].getRoute()[j+1];
				Edge edge=new Edge(s1,s2,s1.getCoordinate().getDistance(s2.getCoordinate()),LineArr[i]);
				edgeList.add(edge);
			}
		}
		
	}
	
	
	public void peopleArrive(){
		//look up in the population objects to see if new people should be created to arrive at each station
		//for each new person, create him using	public Person(int arrivalTime, Station dest, Station source, Station next)
		//by also looking up the routing table
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
