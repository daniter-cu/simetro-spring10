
public class Station {
	private String name;
	private RoutingTab[] routingTable;
	private Coordinate coordinate;
	private Population pop;
	
	
	
	public Station() {
		super();
	}

	public Station(String name, Coordinate coordinate) {
		super();
		this.name = name;
		this.coordinate = coordinate;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setRoutingTable(RoutingTab[] routingTable) {
		this.routingTable = routingTable;
	}
	public RoutingTab[] getRoutingTable() {
		return routingTable;
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

	

	
	
}
