package SIMBack;
import java.util.ArrayList;


public class Person implements Cloneable{
	private int arrivalTime;
	private int boardingTime;
	private int departTime;
	private ArrayList<TimePair> transferTime=new ArrayList<TimePair>();
	private Station dest;
	private Station source;
	private Station next;
        //private Station current;
	//private Line line;
	
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
                //this.current=source;
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
        public void addTimePair() {
	        this.transferTime.add(new TimePair(this.arrivalTime,this.boardingTime));
	}
	public ArrayList<TimePair> getTransferTime() {
		return transferTime;
	}
        /*
        public Line getLine(){
                ArrayList<RoutingTab> table=current.getRoutingTable();
                for(RoutingTab tab : table){
                    if(tab.getDest().getName().equals(dest.getName())&&tab.getNext().getName().equals(next.getName()))
                        line=tab.getLine();
                }
                return line;
        }
         *
         */
	
	public Person clone(){
        try{
          Person cloned = (Person)super.clone();
          cloned.arrivalTime = arrivalTime;
        // cloned.line=(Line)line.clone();
      	//cloned.arrivalTime=arrivalTime;
          cloned.boardingTime = boardingTime;
          cloned.departTime = departTime;
          cloned.transferTime = (ArrayList<TimePair>) transferTime.clone();
          
          //clone station class
          cloned.dest = dest.clone();
          cloned.source = source.clone();
          cloned.next = next.clone();
      	
          return cloned;
        }
        catch(CloneNotSupportedException e){
          System.out.println(e);
          return null;
        }
      }

}
