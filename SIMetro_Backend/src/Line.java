
public class Line {
	private String name;
	private double rate;
	private double speed;
	private int capacity;
	private Station[] route;
	
	

	public Line() {
		super();
	}
	public Line(String name, double rate, double speed, int capacity, Station[] route) {
		super();
		this.name = name;
		this.rate = rate;
		this.speed = speed;
		this.capacity = capacity;
		this.route = route;
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
	public void setRoute(Station[] route) {
		this.route = route;
	}
	public Station[] getRoute() {
		return route;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getCapacity() {
		return capacity;
	}

}
