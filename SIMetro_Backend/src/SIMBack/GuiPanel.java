package SIMBack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GuiPanel extends Panel {
	private ArrayList<Station> stationList;
	BufferedImage imageTrain;
	//    ArrayList<BufferedImage> stationImg=new ArrayList<BufferedImage>();
	//    ArrayList<BufferedImage> trainImg=new ArrayList<BufferedImage>();
	ArrayList<Edge> edgeList=new ArrayList<Edge>();
	ArrayList<ArrayList<Train>> allTrains=new ArrayList<ArrayList<Train>>();
	BufferedImage imageStation;
	//    BufferedImage imageStation2;
	//    BufferedImage imageStation3;
	//    BufferedImage imageStation4;
	//    int xCor = 50;
	//    int yCor = 0;
	int Time=0;

	public GuiPanel(ArrayList<Station> stationList,ArrayList<Line> lineList,ArrayList<ArrayList<Train>> allTrains) {
		try {
			for(int i=0;i<lineList.size();i++) {
				for(int j=0;j<lineList.get(i).getRoute().size()-1;j++) {
					Station s1=lineList.get(i).getRoute().get(j);
					Station s2=lineList.get(i).getRoute().get(j+1);
					Edge edge=new Edge(s1,s2,s1.getCoordinate().getDistance(s2.getCoordinate()),(Math.round((s1.getCoordinate().getDistance(s2.getCoordinate())/lineList.get(i).getSpeed()*10.0)))/10.0,lineList.get(i));				
					edgeList.add(edge);
				}

			}
			this.stationList=stationList;
			this.allTrains=allTrains;

			String imageName = "src/SIMBack/resources/Train.jpg";
			String imageName2 = "src/SIMBack/resources/Station.jpg";
			File input = new File(imageName);
			imageTrain = ImageIO.read(input);
			File input2 = new File(imageName2);
			imageStation = ImageIO.read(input2);
			//            imageStation2 = ImageIO.read(input2);
			//            imageStation3 = ImageIO.read(input2);
			//            imageStation4 = ImageIO.read(input2);

			//        	for(int i=0;i<stationList.size();i++) {
			//        		BufferedImage bi=ImageIO.read(input2);
			//        		stationImg.add(bi);
			//        	}
			//        	for(int i=0;i<allTrains.get(Time).size();i++) {
			//        		BufferedImage bi=ImageIO.read(input);
			//        		trainImg.add(bi);
			//        	}
		} catch (IOException ie) {
			System.out.println("Error:" + ie.getMessage());
		}
	}

	public void paint(Graphics g) {

		//        g.drawImage(imageStation1, 50, 0, null);
		//        g.drawImage(imageStation2, 50, 200, null);
		//        g.drawImage(imageStation3, 50, 400, null);
		//        g.drawImage(imageStation4, 450, 200, null);



		for(int i=0;i<edgeList.size();i++) {
			Edge t=edgeList.get(i);
			int x1=(int) t.getS1().getCoordinate().getX();
			int y1=(int) t.getS1().getCoordinate().getY();
			int x2=(int) t.getS2().getCoordinate().getX();
			int y2=(int) t.getS2().getCoordinate().getY();
			x1=x1*40+50+60;
			x2=x2*40+50+60;
			y1=650-y1*40+40;
			y2=650-y2*40+40;
			g.setColor(Color.lightGray);
			int X[]= {x1-5,x1+5,x2+5,x2-5};
			int Y[]= {y1+5,y1-5,y2-5,y2+5};
			g.fillPolygon(X,Y,4);

			int X1[]= {x1+5,x1-5,x2-5,x2+5};
			int Y1[]= {y1+5,y1-5,y2-5,y2+5};
			g.fillPolygon(X1,Y1,4);

			int X2[]= {x1,x1,x2,x2};
			int Y2[]= {y1+7,y1-7,y2-7,y2+7};
			g.fillPolygon(X2,Y2,4);

			int X3[]= {x1-7,x1+7,x2+7,x2-7};
			int Y3[]= {y1,y1,y2,y2};
			g.fillPolygon(X3,Y3,4);
			g.setColor(Color.yellow);
			g.drawLine(x1,y1,x2,y2);
			g.setColor(Color.blue);
			g.drawString(t.getLine().getName(),(x1+x2)/2-5,(y1+y2)/2-2);
			//        	g.drawLine(x1+10,y1+10,x2+10,y2+10);
			//        	g.drawLine(x1+10,y1-10,x2+10,y2-10);
		}
		g.setColor(Color.red);
		for(int i=0;i<stationList.size();i++) {
			g.drawImage(imageStation, (int) (50+stationList.get(i).getCoordinate().getX()*40), (int)(650-stationList.get(i).getCoordinate().getY()*40), null);
			g.drawString("Station: "+stationList.get(i).getName(), (int) (70+stationList.get(i).getCoordinate().getX()*40), (int)(720-stationList.get(i).getCoordinate().getY()*40));
		}
		if(Time<allTrains.size())
			for(int i=0;i<allTrains.get(Time).size();i++) {
				double x=allTrains.get(Time).get(i).getCoordinate().getX();
				double y=allTrains.get(Time).get(i).getCoordinate().getY();
				g.drawImage(imageTrain, (int)(50+40*x), (int)(650-40*y), null);

			}

		//        g.drawImage(imageTrain, xCor, yCor, null);
		//        yCor+=5;
		Time++;
		if (Time==allTrains.size())
			Time=0;
		g.setColor(Color.black);
		g.drawString("Time="+Time, 20, 20);

	}

	public void setStationList(ArrayList<Station> stationList) {
		this.stationList = stationList;
	}

	public ArrayList<Station> getStationList() {
		return stationList;
	}


}
