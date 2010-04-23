
public class Coordinate {
	private double x;
	private double y;
	
	public double getDistance(Coordinate coor) {
		return Math.sqrt((x-coor.getX())*(x-coor.getX())+(y-coor.getY())*(y-coor.getY()));
	}
	
	public Coordinate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Coordinate(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public double getX() {
		return x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getY() {
		return y;
	}
	

}
