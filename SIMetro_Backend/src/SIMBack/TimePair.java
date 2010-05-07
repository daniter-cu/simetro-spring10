package SIMBack;

public class TimePair {
	private int arrivalTime;
	private int departTime;
	
	public TimePair() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TimePair(int arrivalTime, int departTime) {
		super();
		this.arrivalTime = arrivalTime;
		this.departTime = departTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setDepartTime(int departTime) {
		this.departTime = departTime;
	}
	public int getDepartTime() {
		return departTime;
	}
	

}
