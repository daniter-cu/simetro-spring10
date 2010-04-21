
public class Train {
	private Line line;
	private int arrivalTime;
	private Coordinate coordinate;
	private int capacity;
	private double speed;
	
	
	public Train() {
		super();
	}
	public Train(Line line, int arrivalTime, Coordinate coordinate,
			int capacity, double speed) {
		super();
		this.line = line;
		this.arrivalTime = arrivalTime;
		this.coordinate = coordinate;
		this.capacity = capacity;
		this.speed = speed;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public Line getLine() {
		return line;
	}
	public void setArriveTime(int arriveTime) {
		this.arrivalTime = arriveTime;
	}
	public int getArriveTime() {
		return arrivalTime;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public double getSpeed() {
		return speed;
	}
	
}
