


public class Simulate {
	public void createRoutingTables() {
		//for each station, create its routing table and link it to the station
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
