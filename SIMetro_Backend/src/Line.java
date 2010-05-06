import java.util.ArrayList;


public class Line {
	private String name;
	private double rate;
	private double speed;
	private int capacity;
	private ArrayList<Station> route;
	
	

	public Line() {
		super();
	}

	public Line(String name) {
		super();
		this.name = name;
	}

	public Line(String name, double rate, double speed, int capacity, ArrayList<Station> route) {
		super();
		this.name = name;
		this.rate = rate;
		this.speed = speed;
		this.capacity = capacity;
		this.setRoute(route);
		System.out.println("Line created: "+name+"\tRate: "+rate+"\tSpeed:"+speed+"\tCapacity:"+capacity);
		System.out.print("              ");
		for(int i=0;i<route.size()-1;i++) {
			System.out.print(route.get(i).getName()+"<==>");
		}
		System.out.println(route.get(route.size()-1).getName());
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public double getRate() {
		return rate;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public double getSpeed() {
		return speed;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getCapacity() {
		return capacity;
	}

	public void setRoute(ArrayList<Station> route) {
		this.route = route;
	}

	public ArrayList<Station> getRoute() {
		return route;
	}

}
