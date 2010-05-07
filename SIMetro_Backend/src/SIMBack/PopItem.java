package SIMBack;

public class PopItem {
	private Station dest;
	private double rate;
	
	public PopItem() {
		super();
	}
	public PopItem(Station dest, double rate) {
		super();
		this.dest = dest;
		this.rate = rate;
	}
	public void setDest(Station dest) {
		this.dest = dest;
	}
	public Station getDest() {
		return dest;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public double getRate() {
		return rate;
	}
	

}
