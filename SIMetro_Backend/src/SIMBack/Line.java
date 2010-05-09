package SIMBack;
import java.util.ArrayList;


public class Line implements Cloneable {
	private String name;
	private double rate;
	private double speed;
	private int capacity;
	private ArrayList<Station> route;
    private ArrayList<Edge> edges;
    private Line rvsLine;
	
    public Line clone(){
    	try{
    	Line cloned= (Line)super.clone();
    	cloned.name=this.name;
    	cloned.speed=this.speed;
    	cloned.capacity=this.capacity;
    	cloned.route=(ArrayList<Station>)route.clone();
    	cloned.edges=(ArrayList<Edge>)edges.clone();
    	cloned.rvsLine=(Line)rvsLine.clone();
    	return cloned;
    	}catch(Exception e){
    		e.printStackTrace();   	
    		return null;
    	}
    	
    }
    
    public void print() {
    	System.out.println("Line: "+name+"\tRate: "+rate+"\tSpeed:"+speed+"\tCapacity:"+capacity);
    	System.out.print("Route:   ");
		for(int i=0;i<route.size()-1;i++) {
			System.out.print(route.get(i).getName()+"<==>");
		}
		System.out.println(route.get(route.size()-1).getName());
    }

	public Line() {
		super();
	}

	public Line(String name) {
		super();
		this.name = name;
	}

	public Line(String name, double rate, double speed, int capacity, ArrayList<Station> route) {
		super();
		this.name = name;
		this.rate = rate;
		this.speed = speed;
		this.capacity = capacity;
                this.route=route;
                this.edges=new ArrayList<Edge>();
		System.out.println("Line created: "+name+"\tRate: "+rate+"\tSpeed:"+speed+"\tCapacity:"+capacity);
		System.out.print("              ");
		for(int i=0;i<route.size()-1;i++) {
			System.out.print(route.get(i).getName()+"<==>");
		}
		System.out.println(route.get(route.size()-1).getName());
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public double getRate() {
		return rate;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public double getSpeed() {
		return speed;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getCapacity() {
		return capacity;
	}

	public void setRoute(ArrayList<Station> route) {
		this.route = route;
	}

	public ArrayList<Station> getRoute() {
		return route;
	}

        public void setEdges(ArrayList<Edge> edges){
                this.edges=edges;
        }
        
        public void addEdge(Edge edge){
                edges.add(edge);
                //rvsLine.addEdge(edge.getRvsEdge());
                

        }

        public void setRvsEdges(){
            System.out.println("Reverse edges of "+name+" set");
            for(int i=edges.size()-1;i>=0;i--)
            {
                edges.get(i).setRvsEdge();
                rvsLine.addEdge(edges.get(i).getRvsEdge());
            }
        }

        public Edge getEdge(int edgeIndex){
                
                return edges.get(edgeIndex);
        }

        public int edgeNumber(){
                return this.edges.size();
        }

        public ArrayList<Edge> getEdges(){
                return edges;
        }

        public void setRvsLine(){
                ArrayList<Station> rvsRoute=new ArrayList<Station>();
                //System.out.println(route.size());
                    for(int i=route.size()-1;i>=0;i--)
                    {
                        rvsRoute.add(route.get(i));
                        //System.out.print(line.getRoute().get(i).getName());
                    }
                    System.out.println("\nReverse Direction of "+name+" is Created:");
                    Line rLine=new Line(name, rate, speed, capacity,rvsRoute);
                    rvsLine=rLine;
        }

        public Line getRvsLine(){
                    return rvsLine;
        }
}
