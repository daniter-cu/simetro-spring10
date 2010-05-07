package SIMBack;
import java.util.ArrayList;


public class Station {
	private String name;
	private ArrayList<RoutingTab> routingTable;
	private Coordinate coordinate;
	private Population pop;
	
	
	
	public Station() {
		super();
	}

	public Station(String name) {
		super();
		this.name = name;
	}

	public Station(String name, Coordinate coordinate) {
		super();
		this.name = name;
		this.coordinate = coordinate;
		System.out.println("Station Created: "+this.name+"\tCoordinates:"+coordinate.getX()+"\t"+coordinate.getY());
	}
	
	public RoutingTab getRTabByDest(Station dest) {
		for(int i=0;i<getRoutingTable().size();i++) {
			if(getRoutingTable().get(i).getDest().equals(dest)) {
				return getRoutingTable().get(i);
			}
		}
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setPop(Population pop) {
		this.pop = pop;
	}
	public Population getPop() {
		return pop;
	}

	public void setRoutingTable(ArrayList<RoutingTab> routingTable) {
		this.routingTable = routingTable;
	}

	public ArrayList<RoutingTab> getRoutingTable() {
		return routingTable;
	}

	

	
	
}
