
public class Edge {
	private Station s1;
	private Station s2;
	private double dist;
	private Line line;
	
	
	
	public Edge(Station s1, Station s2, double dist, Line line) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.dist = dist;
		this.line = line;
	}
	
	public void setS1(Station s1) {
		this.s1 = s1;
	}
	public Station getS1() {
		return s1;
	}
	public void setS2(Station s2) {
		this.s2 = s2;
	}
	public Station getS2() {
		return s2;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
	public double getDist() {
		return dist;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public Line getLine() {
		return line;
	}
	

}
