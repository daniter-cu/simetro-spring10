package SIMBack;
import java.util.ArrayList;

public class Train implements Cloneable{
	private Line line;
	private int arrivalTime;
	private Coordinate coordinate;
	private int capacity;
	//private double speed;
        private int onEdge;
        private double DistLeft;
	private ArrayList<Person> board;
	
	public Train() {
		super();
	}
	public Train(Line line, int arrivalTime, Coordinate coordinate,
			int capacity, double speed,boolean reverse) {
		super();
                
               if(reverse)
                {
                    this.line=line.getRvsLine();
                    //System.out.println("this train is on reverse direction of "+line.getName());
                }
                else this.line=line;
                this.arrivalTime = arrivalTime;
		this.coordinate = coordinate;
		this.capacity = capacity;
		//this.speed = speed;
                this.onEdge=0;
                this.DistLeft=1+(int)this.line.getEdge(0).getDist();
                board=new ArrayList<Person>();
	}


	public void setLine(Line line) {
		this.line = line;
	}
	public Line getLine() {
		return line;
	}
	public void setArriveTime(int arriveTime) {
		this.arrivalTime = arriveTime;
	}
	public int getArriveTime() {
		return arrivalTime;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setSpeed(int speed) {
		//this.speed = speed;
	}
	public double getSpeed() {
		return line.getSpeed();
	}

        public void nextEdge(){
                onEdge++;
                DistLeft=this.line.getEdge(onEdge).getDist();
        }

        public boolean arriveNextStop(){
                double temp=DistLeft-line.getSpeed();
                if(temp>0)
                {
                    DistLeft=temp;
//                    System.out.println("*****A train on the "+onEdge+"th edge of line "+line.getName()+" is running between "+line.getEdge(onEdge).getS1().getName()+" => "+line.getEdge(onEdge).getS2().getName()+" distance left: "+temp);
                    return false;
                }
                else 
                {
//                    System.out.println("*****A train on line "+line.getName()+" arrives at edge "+line.getEdge(onEdge).getS2().getName());
                    return true;
                }
        }

        public boolean arriveDest(){
                if(line.edgeNumber()==onEdge+1)
                {
                    System.out.println("On line "+line.getName()+": a Train gets to its Destination!");
                    return true;
                }
                else return false;
        }

        public ArrayList<Person> getBoard(){
                return board;
        }

        public void addPerson(Person person){
                board.add(person);
        }

        public void removePerson(Person person){
                board.remove(person);
        }

        public Station getNext(){
                return this.line.getEdge(onEdge).getS2();
        }

        public Station getCurrent(){
                //System.out.println("Line is "+line.getName()+" Edge is "+onEdge);
                return line.getEdge(onEdge).getS1();
        }

        public void updateCoordinate(){
                Coordinate source=this.getCurrent().getCoordinate();
                Coordinate dest=this.getNext().getCoordinate();
                Coordinate tr=coordinate;
                if(tr.inBetween(source, dest))
                {
                        double fraction=line.getSpeed()/source.getDistance(dest);
                        //System.out.println("fraction is "+fraction);
                        double x=tr.getX()+(dest.getX()-source.getX())*fraction;
                        double y=tr.getY()+(dest.getY()-source.getY())*fraction;
                        this.setCoordinate(new Coordinate(x,y));
                }
                else
                {
                        double x=dest.getX();
                        double y=dest.getY();
                        this.setCoordinate(new Coordinate(x,y));
                }
                //System.out.println("The new coordinate of this train on "+line.getName()+" is: ( "+coordinate.getX()+" , "+coordinate.getY()+" )");
        }
        
        public Train clone(){
            try{
              Train cloned = (Train)super.clone();
              cloned.arrivalTime = arrivalTime;
            // cloned.line=(Line)line.clone();
          	cloned.arrivalTime=arrivalTime;
          	cloned.coordinate=new Coordinate(coordinate.getX(),coordinate.getY());
          	cloned.capacity=capacity;
          	
            cloned.onEdge=onEdge;
            cloned.DistLeft=DistLeft;
          	cloned.board=(ArrayList<Person>)board.clone();
              return cloned;
            }
            catch(CloneNotSupportedException e){
              System.out.println(e);
              return null;
            }
          }

}
