package SIMBack;
import java.util.ArrayList;

public class Train {
	private Line line;
	private int arrivalTime;
	private Coordinate coordinate;
	private int capacity;
	private double speed;
        private int onEdge;
        private double timeDistLeft;
	private ArrayList<Person> board;
	
	public Train() {
		super();
	}
	public Train(Line line, int arrivalTime, Coordinate coordinate,
			int capacity, double speed,boolean reverse) {
		super();
                
               if(reverse)
                {
                    ArrayList<Station> rvsRoute=new ArrayList<Station>();
                    for(int i=line.getRoute().size()-1;i>=0;i--)
                    {
                        rvsRoute.add(line.getRoute().get(i));
                        //System.out.print(line.getRoute().get(i).getName());
                    }
                    System.out.println("\nReverse Direction of "+line.getName()+" is Created:");
                    Line rvsLine=new Line(line.getName(), line.getRate(), line.getSpeed(), line.getCapacity(),rvsRoute);
                    rvsLine.setEdges(line.getEdges());
                    ArrayList<Edge> rvsEdges=new ArrayList<Edge>();
                   

                    for(int i=rvsLine.edgeNumber()-1;i>=0;i--)
                    {
                        Edge temp=new Edge(line.getEdge(i).getS2(), line.getEdge(i).getS1(), line.getEdge(i).getDist(),line.getEdge(i).getTimedist(), line.getEdge(i).getLine());
                        rvsEdges.add(temp);
                    }
                    System.out.println();
                    rvsLine.setEdges(rvsEdges);
                    this.line=rvsLine;
                }
                else this.line=line;

		this.arrivalTime = arrivalTime;
		this.coordinate = coordinate;
		this.capacity = capacity;
		this.speed = speed;
                this.onEdge=0;
                this.timeDistLeft=this.line.getEdge(0).getTimedist();
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
		this.speed = speed;
	}
	public double getSpeed() {
		return speed;
	}

        public void nextEdge(){
                onEdge++;
                timeDistLeft=line.getEdge(onEdge).getTimedist();
        }

        public boolean arriveNextStop(){
                double temp=timeDistLeft-1;
                if(temp>0)
                {
                    timeDistLeft=temp;
                    System.out.println("*****A train on "+line.getName()+" is running between "+line.getEdge(onEdge).getS1().getName()+" => "+line.getEdge(onEdge).getS2().getName()+"time distance left: "+temp);
                    return false;
                }
                else 
                {
                    System.out.println("*****A train on "+line.getName()+" arrives "+line.getEdge(onEdge).getS2().getName());
                    return true;
                }
        }

        public boolean arriveDest(){
                if(line.edgeNumber()==onEdge+1)
                {
                    System.out.println("On "+line.getName()+": a Train get Destination!");
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
                return this.line.getEdge(onEdge).getS1();
        }
}
