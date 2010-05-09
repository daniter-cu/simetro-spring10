package SIMBack;
import java.util.ArrayList;


public class Station implements Cloneable{
	private String name;
	private ArrayList<RoutingTab> routingTable;
	private Coordinate coordinate;
	private Population pop;
	private ArrayList<Person> crowd=new ArrayList<Person>();


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
	
	public void changeRate(Station s2,double rate) {
		if(pop!=null) {
			if(pop.getPopItemArr()!=null) {
				for(int i=0;i<pop.getPopItemArr().size();i++) {
					if(pop.getPopItemArr().get(i).getClass().equals(s2)) {
						pop.getPopItemArr().get(i).setRate(rate);
					}
				}
			}
		}
	}

	public RoutingTab getRTabByDest(Station dest) {
		for(int i=0;i<getRoutingTable().size();i++) {
			if(getRoutingTable().get(i).getDest().equals(dest)) {
				return getRoutingTable().get(i);
			}
		}
		return null;
	}

	public void print() {
		System.out.println(name);
		System.out.println("Coordinates ("+coordinate.getX()+","+coordinate.getY()+");");
		if(pop!=null) {
			System.out.println("Population:");
			for(int i=0;i<pop.getPopItemArr().size();i++) {
				PopItem a=pop.getPopItemArr().get(i);			
				System.out.println("("+a.getDest().getName()+", "+a.getRate()+")");
			}
		}
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

	public ArrayList<Person> getCrowd(){
		return crowd;
	}

	public void addPerson(Person person){
		crowd.add(person);
	}

	public void removePerson(Person person){
		crowd.remove(person);
	}

	public Station findNext(Station dest){
		for(RoutingTab rt : routingTable)
		{
			if(rt.getDest().getName().equals(dest.getName()))
				return rt.getNext();
		}
		return this;
	}

	public Station clone(){
		try{
			Station cloned = (Station)super.clone();

			cloned.name = name;

			//clone routing table
			cloned.routingTable = (ArrayList<RoutingTab>) routingTable.clone();

			cloned.coordinate = new Coordinate(coordinate.getX(),coordinate.getY());

			//clone population
			cloned.pop = pop.clone();

			cloned.crowd = (ArrayList<Person>) crowd.clone();

			return cloned;
		}
		catch(CloneNotSupportedException e){
			System.out.println(e);
			return null;
		}
	}

}

//}
