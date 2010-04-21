import java.util.ArrayList;


public class Person {
	private int arrivalTime;
	private int bordingTime;
	private int departTime;
	private ArrayList<TimePair> transferTime;
	private Station dest;
	private Station source;
	private Station next;
	
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Person(int arrivalTime, Station dest, Station source, Station next) {
		super();
		this.arrivalTime = arrivalTime;
		this.dest = dest;
		this.source = source;
		this.next = next;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setBordingTime(int bordingTime) {
		this.bordingTime = bordingTime;
	}
	public int getBordingTime() {
		return bordingTime;
	}
	public void setDepartTime(int departTime) {
		this.departTime = departTime;
	}
	public int getDepartTime() {
		return departTime;
	}
	public void setDest(Station dest) {
		this.dest = dest;
	}
	public Station getDest() {
		return dest;
	}
	public void setSource(Station source) {
		this.source = source;
	}
	public Station getSource() {
		return source;
	}
	public void setNext(Station next) {
		this.next = next;
	}
	public Station getNext() {
		return next;
	}
	public void setTransferTime(ArrayList<TimePair> transferTime) {
		this.transferTime = transferTime;
	}
	public ArrayList<TimePair> getTransferTime() {
		return transferTime;
	}

}
