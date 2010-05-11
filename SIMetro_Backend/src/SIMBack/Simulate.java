package SIMBack;
import java.util.ArrayList;



public class Simulate {
	ArrayList<Person> persons=new ArrayList<Person>();
	ArrayList<Train> trains=new ArrayList<Train>();

	//create timeline object
	//TimeLine tl = new TimeLine();

	//public void createTimeLine(int timeRange){
	//this is for setting timeline
	// a timeRange is mandatory when creating new simulation
	// TimeLine tl=new TimeLine(timeRange);
	//}

	public void changeRate(Station sa,Station sb,double rate) {
		sa.changeRate(sb, rate);
	}
	
	public void changeCapacity(Line l,int cap) {
		l.changeCapacity(cap);
	}
	
	public void changeFrequency(Line l,double frq) {
		l.changeFrequency(frq);
	}
	
	public void changeSpeed(Line l, double spd) {
		l.changeSpeed(spd);
	}
	public void createRoutingTables(ArrayList<Station> stationList,ArrayList<Line> lineList) {
		//for each station, create its routing table and link it to the station
		ArrayList<Edge> edgeList=new ArrayList<Edge>();
		for(int i=0;i<lineList.size();i++) {
			for(int j=0;j<lineList.get(i).getRoute().size()-1;j++) {
				Station s1=lineList.get(i).getRoute().get(j);
				Station s2=lineList.get(i).getRoute().get(j+1);
				Edge edge=new Edge(s1,s2,s1.getCoordinate().getDistance(s2.getCoordinate()),(Math.round((s1.getCoordinate().getDistance(s2.getCoordinate())/lineList.get(i).getSpeed()*10.0)))/10.0,lineList.get(i));
				lineList.get(i).addEdge(edge);
				edgeList.add(edge);
				//System.out.println("A new edge is added: "+edge.getS1().getName()+" <=> "+edge.getS2().getName());
			}
			lineList.get(i).setRvsEdges();
		}

		//System.out.println("edgeList.size() "+edgeList.size());

		for(int si=0;si<stationList.size();si++) {
			ArrayList<RoutingTab> RoutingTable=new ArrayList<RoutingTab>();
			int sli=0;
			for(int rti=0;rti<stationList.size()-1;rti++) {
				if(sli==si)
					sli++;
				RoutingTab rt=new RoutingTab();
				rt.setDest(stationList.get(sli));
				rt.setDist(9999);
				RoutingTable.add(rt);
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
			for(int j=0;j<stationList.get(i).getRoutingTable().size();j++) {
				System.out.println("Dest: "+stationList.get(i).getRoutingTable().get(j).getDest().getName()
						+"\tDist: "+stationList.get(i).getRoutingTable().get(j).getDist()
						+"\tLine: "+stationList.get(i).getRoutingTable().get(j).getLine().getName()
						+"\tNext: "+stationList.get(i).getRoutingTable().get(j).getNext().getName());
			}
		}
		//		System.out.println("===="+stationList.get(0).getRTabByDest(stationList.get(1)).getDist());
		System.out.println("Constructing...");
		for(int i=0;i<stationList.size()-1;i++) {
			//			System.out.println("i:"+i);
			for(int si=0;si<stationList.size();si++) {
				//				System.out.println("si:"+si);
				for(int rti=0;rti<stationList.get(si).getRoutingTable().size();rti++) {
					//					System.out.println("rti:"+rti);
					Station source=stationList.get(si);
					Station dest=stationList.get(si).getRoutingTable().get(rti).getDest();
					for(int tr=0;tr<stationList.size()-1;tr++) {
						if((!source.getRoutingTable().get(tr).equals(dest))&&(!source.getRoutingTable().get(tr).getDest().equals(dest))) {
							//							System.out.println(source.getRoutingTable()[tr].getDist());
							//							System.out.println("+++++"+source.getRoutingTable()[tr].getDest().getName());
							//							System.out.println(dest.getName());
							//							System.out.println(source.getRoutingTable()[tr].getDest().getRTabByDest(dest).getDist());

							double newdist=source.getRoutingTable().get(tr).getDist()+source.getRoutingTable().get(tr).getDest().getRTabByDest(dest).getDist();
							if (newdist<source.getRoutingTable().get(rti).getDist()) {
								source.getRoutingTable().get(rti).setDist(newdist);
								source.getRoutingTable().get(rti).setLine(source.getRoutingTable().get(tr).getLine());
								source.getRoutingTable().get(rti).setNext(source.getRoutingTable().get(tr).getNext());
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
			for(int j=0;j<stationList.get(i).getRoutingTable().size();j++) {
				System.out.println("Dest: "+stationList.get(i).getRoutingTable().get(j).getDest().getName()
						+"\tDist: "+stationList.get(i).getRoutingTable().get(j).getDist()
						+"\tLine: "+stationList.get(i).getRoutingTable().get(j).getLine().getName()
						+"\tNext: "+stationList.get(i).getRoutingTable().get(j).getNext().getName());
			}
		}


	}


	public void peopleArrive(ArrayList<Station> stationList, int arriveTime,TimeLine tl){
		//look up in the population objects to see if new people should be created to arrive at each station
		//for each new person, create him using	public Person(int arrivalTime, Station dest, Station source, Station next)
		//by also looking up the routing table
		System.out.println("----------------ARRIVING PASSENGER INFOMATION:-----------------");
		//System.out.println("current time from peopleArrive is " + arriveTime);
		//iterate through station list
		for(int si=0;si<stationList.size();si++) {

			//get population for station
			Population aPop = stationList.get(si).getPop();

			if (aPop != null){

				//get pop item array associated with popullation
				ArrayList<PopItem> popItemArray = aPop.getPopItemArr();

				//iterate through pop item array
				for (PopItem anItem : popItemArray){

					//get destination and rate
					Station destStation = anItem.getDest();
					String destStationName = destStation.getName();

					int popRate = (int)anItem.getRate();
					int peopleCount = 0;

					//create a new person up to the rate value
					//while (peopleCount < popRate){

						//Parameters are as follows: arrivalTime = arriveTime, StationDest = destStation,
						//StationSource = stationList.get(si), Station next = get from routing table

						Station sourceStation = stationList.get(si);

						ArrayList<RoutingTab> aTable = sourceStation.getRoutingTable();


						for(int j=0; j < aTable.size(); j++) {


							//determine nextStation according to destination for each person
							if(aTable.get(j).getDest().equals(destStation)){
								//get next station on routing table
								Station nextStation = aTable.get(j).getNext();

								while (peopleCount < popRate){
									//create new person
									Person aPerson = new Person(arriveTime, destStation, sourceStation, nextStation);
									tl.addPerson(aPerson);
									//add new person to list linked with station
									sourceStation.addPerson(aPerson);
									persons.add(aPerson);
									//System.out.println("New person created at time " + arriveTime + " arriving at station " + sourceStation.getName() + " going to " + destStation.getName() + ", next station is "+ nextStation.getName());
									//increment peopleCount
									peopleCount++;

									//add new person to persons array list

								}//end of while

								//break the loop so that it does not keep looking up the routing table	
								break;
							}//end of if

						}

					//}
					//}

				}
			}
		}
		//set persons in timeline
		//tl.setTime(arriveTime);
		//tl.setPersons(persons);
		System.out.println("-----------------------------END---------------------------");
	}

	public void trainArrive(ArrayList<Line> lineList, int currTime){
		//look up in the line objects to see if new trains should be created to arrive at each station
		//using public Train(Line line, int arrivalTime, Coordinate coordinate,int capacity, double speed)

		int timeCreate = 0;
		System.out.println("\n----------------TRAIN CREATING INFOMATION:-----------------");

		//iterate through list of lines
		for(int i=0;i<lineList.size();i++) {

			Line aLine = lineList.get(i);

			double lineRate = aLine.getRate();
			double lineSpeed = aLine.getSpeed();
			ArrayList<Station> lineRoute = aLine.getRoute();
			int lineCap = aLine.getCapacity();


			//if train is created at fractional rate, divide it by 1
			if (lineRate <= 1){
				timeCreate = (int)(1 / lineRate);
			}

			//else just make rate an int
			else
				timeCreate = (int)lineRate;
			//System.out.println("Train should be created every " + timeCreate + " minutes");
			
			//if current time is 0 or time mod line rate is 0, it's time for new trains to be created at first and last station of line
			if (currTime == 0 || currTime % timeCreate == 0){
				//System.out.println("Time to create new train");

				//get first station of line
				Station firstStation = lineRoute.get(0);

				//get station coords
				Coordinate firstCoords = firstStation.getCoordinate();

				int lineLength = lineRoute.size();

				//get last station of line
				//make sure this doesn't give you an off by one error
				Station lastStation = lineRoute.get(lineLength-1);

				//get station coords
				Coordinate lastCoords = lastStation.getCoordinate();

				//Train(Line line, int arrivalTime, Coordinate coordinate,int capacity, double speed)
				//now create a train at first station
				Train firstTrain = new Train(aLine, currTime, firstCoords, lineCap, lineSpeed, false);
				//System.out.println("The coordinate of the train on "+firstTrain.getLine().getName()+" is ( "+firstTrain.getCoordinate().getX()+" , "+firstTrain.getCoordinate().getY()+" )");
				//System.out.print(firstTrain.getCurrent().getName()+" Station Coordinate:");
				//firstTrain.getCoordinate().printCoor(firstCoords);
				//System.out.println();
				trains.add(firstTrain);
				System.out.println("New train created at time " + currTime + " on line " + aLine.getName() + " at station " + firstStation.getName());
				//now create a train at last station
				Train endTrain = new Train(aLine, currTime, lastCoords, lineCap, lineSpeed, true);
				trains.add(endTrain);
				System.out.println("New train created at time " + currTime + " on line " + aLine.getName() + " at station " + lastStation.getName());

			}

		}
		//tl.setTime(currTime);
		//tl.setTrains(trains);
		System.out.println("---------------------------END------------------------------");
	}

	public void trainMove(int currTime, TimeLine tl) {
		//update the coordinates of each train running on lines
		//if the train arrives at a station, unload the people that wants to get off this train, check if these people has already reached their destination (add the departure time),
		//                                                                                             or do they need to transfer to another line (add new arrival time using public void setTransferTime(ArrayList<TimePair> transferTime))
		//                                   load the people that are waiting to get on this train

		ArrayList<Train> trainInDest=new ArrayList<Train>();
		System.out.println("\n-------------------------TRANSFER INFOMATION:-------------------------");
		for (Train aTrain : trains)
		{
			//System.out.println("The coordinate of the train on "+aTrain.getLine().getName()+" is ( "+aTrain.getCoordinate().getX()+" , "+aTrain.getCoordinate().getY()+" )");
			//System.out.print(aTrain.getCurrent().getName()+" Station Coordinate:");
			//          aTrain.getCoordinate().printCoor(aTrain.getCurrent().getCoordinate());
			//          System.out.println();
			tl.addTrain(aTrain);
			aTrain.updateCoordinate();
		}
		tl.addAllTrains();

		for (Train aTrain : trains)
		{

			if (currTime==aTrain.getArriveTime())
			{
				System.out.println("A new train on line "+aTrain.getLine().getName()+" is going to pick up passengers in Station "+aTrain.getCurrent().getName());
				transfer(aTrain, currTime);
				continue;
			}
			if(aTrain.arriveNextStop())
			{
				//System.out.println("*****A Train on "+aTrain.getLine().getName()+" arrives "+aTrain.getNext().getName());
				//aTrain.setArriveTime(currTime);

				if(aTrain.arriveDest())
					trainInDest.add(aTrain);
				else aTrain.nextEdge();
				transfer(aTrain,currTime);
			}
			else continue;

		}
		for (int i=0;i<trainInDest.size();i++)
		{
			trains.remove(trainInDest.get(i));
		}
		tl.addAllPersons();
		System.out.println("The Number of Trains in Operation: "+trains.size());
		System.out.println("----------------------------------END-----------------------------------");

	}

	void transfer(Train train, int currTime){
		ArrayList<Person> personTransferred=new ArrayList<Person>();
		Station station=train.getCurrent();
		ArrayList<Person> crowd=station.getCrowd();
		int personCount=0;
		for(Person aPerson : crowd)
			if(aPerson.getNext().getName().equals(train.getNext().getName()))
			{  
				train.addPerson(aPerson);
				aPerson.setBoardingTime(currTime);
				aPerson.addTimePair();
				personTransferred.add(aPerson);
				personCount++;
				//System.out.println("*****A person get on ["+train.getLine().getName()+"] At ["+station.getName()+"]");
			}
		System.out.println(personCount+" people get on line ["+train.getLine().getName()+"] at station ["+station.getName()+"]" );
		for (int i=0;i<personTransferred.size();i++)
		{
			station.removePerson(personTransferred.get(i));
		}

		ArrayList<Person> board=train.getBoard();
		personTransferred=new ArrayList<Person>();
		for(Person aPerson : board)
			if(aPerson.getNext().getName().equals(station.getName()))
			{
				station.addPerson(aPerson);
				aPerson.setArrivalTime(currTime);
				System.out.println("*****A person gets off line ["+train.getLine().getName()+"] at station ["+station.getName()+"]");
				if(aPerson.getDest().getName().equals(station.getName()))
				{
					aPerson.setDepartTime(currTime);
					System.out.println("*****A person has arrived at his/her destination: "+" ["+station.getName()+"]! Have a good day!!!");
				}
				else {
					aPerson.setNext(station.findNext(aPerson.getDest()));

				}
				personTransferred.add(aPerson);

			}
		for (int i=0;i<personTransferred.size();i++)
		{
			train.removePerson(personTransferred.get(i));
		}

	}
}

