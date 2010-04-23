
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
		System.out.println("Line created: "+name+"  Rate: "+rate+"  Speed:"+speed+" Capacity:"+capacity);
		System.out.print("              ");
		for(int i=0;i<route.length-1;i++) {
			System.out.print(route[i].getName()+"==");
		}
		System.out.println(route[route.length-1].getName());
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
