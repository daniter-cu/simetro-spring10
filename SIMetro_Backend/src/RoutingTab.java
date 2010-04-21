
public class RoutingTab {
	private Station dest;
	private Line line;
	private Station next;
	
		
	
	public RoutingTab() {
		super();
	}
	public RoutingTab(Station dest, Line line, Station next) {
		super();
		this.dest = dest;
		this.line = line;
		this.next = next;
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
	
}
