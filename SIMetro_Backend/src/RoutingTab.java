
public class RoutingTab {
	private Station dest;
	private Line line;
	private Station next;
	private double dist;
		
	
	public RoutingTab() {
		super();
		this.dest=new Station("null");
		this.line=new Line("null");
		this.next=new Station("null");
		this.dist=9999;
	}
	public RoutingTab(Station dest, Line line, Station next) {
		super();
		this.dest = dest;
		this.line = line;
		this.next = next;
		this.dist=9999;
	}
	public void setDest(Station dest) {
		this.dest = dest;
	}
	public Station getDest() {
		return dest;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public Line getLine() {
		return line;
	}
	public void setNext(Station next) {
		this.next = next;
	}
	public Station getNext() {
		return next;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
	public double getDist() {
		return dist;
	}
	
}
