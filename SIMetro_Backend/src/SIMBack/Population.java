package SIMBack;
import java.util.ArrayList;


public class Population {
	private ArrayList<PopItem> popItemArr;

	
	public Population() {
		super();
	}

	public Population(ArrayList<PopItem> popItemArr) {
		super();
		this.setPopItemArr(popItemArr);
	}

	public void setPopItemArr(ArrayList<PopItem> popItemArr) {
		this.popItemArr = popItemArr;
	}

	public ArrayList<PopItem> getPopItemArr() {
		return popItemArr;
	}



}
