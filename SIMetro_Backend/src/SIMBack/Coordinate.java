package SIMBack;
public class Coordinate {
	private double x;
	private double y;
	
	public double getDistance(Coordinate coor) {
		double t= Math.sqrt((x-coor.getX())*(x-coor.getX())+(y-coor.getY())*(y-coor.getY()));
		t=(Math.round(t*10.0))/10.0;
		return t;
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

        public boolean inBetween(Coordinate source, Coordinate dest)
        {
                if(source.getDistance(dest)>source.getDistance(this))
                    return true;
                else return false;
        }

	public void setX(double x) {
		this.x = x;
	}
	public double getX() {
		return x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getY() {
		return y;
	}
	public void printCoor(Coordinate co){
        System.out.print("( "+co.getX()+" , "+co.getY()+" )");

        }

}
