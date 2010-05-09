package SIMBack;
import java.util.ArrayList;


public class Population implements Cloneable{
	private ArrayList<PopItem> popItemArr;

	public Population clone(){
		try{
		Population cloned=(Population)super.clone();
		cloned.popItemArr=(ArrayList<PopItem>)popItemArr.clone();
		return cloned;
		}catch(Exception e){
			e.printStackTrace();
			return null;			
		}
		
	}
	
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
