package SIMBack;
public class TimePair {
	private int arrivalTime;
	private int boardingTime;
	
	public TimePair() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TimePair(int arrivalTime, int boardingTime) {
		super();
		this.arrivalTime = arrivalTime;
		this.boardingTime = boardingTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setBoardingTime(int boardingTime) {
		this.boardingTime = boardingTime;
	}
	public int getBoardingTime() {
		return boardingTime;
	}
	

}
