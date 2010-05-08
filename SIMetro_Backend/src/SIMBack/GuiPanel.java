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
    ArrayList<BufferedImage> stationImg=new ArrayList<BufferedImage>();
    BufferedImage imageStation1;
    BufferedImage imageStation2;
    BufferedImage imageStation3;
    BufferedImage imageStation4;
    int xCor = 50;
    int yCor = 0;

    public GuiPanel(ArrayList<Station> stationList) {
        try {
        	
        	this.stationList=stationList;

            String imageName = "src/SIMBack/resources/Train.jpg";
            String imageName2 = "src/SIMBack/resources/Station.jpg";
            File input = new File(imageName);
            imageTrain = ImageIO.read(input);
            File input2 = new File(imageName2);
//            imageStation1 = ImageIO.read(input2);
//            imageStation2 = ImageIO.read(input2);
//            imageStation3 = ImageIO.read(input2);
//            imageStation4 = ImageIO.read(input2);
            
        	for(int i=0;i<stationList.size();i++) {
        		BufferedImage bi=ImageIO.read(input2);
        		stationImg.add(bi);
        	}
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
    }

    public void paint(Graphics g) {
        g.drawImage(imageTrain, xCor, yCor, null);
//        g.drawImage(imageStation1, 50, 0, null);
//        g.drawImage(imageStation2, 50, 200, null);
//        g.drawImage(imageStation3, 50, 400, null);
//        g.drawImage(imageStation4, 450, 200, null);
        
        for(int i=0;i<stationImg.size();i++) {
        	g.drawImage(stationImg.get(i), (int) (50+stationList.get(i).getCoordinate().getX()*40), (int)(650-stationList.get(i).getCoordinate().getY()*40), null);
        }
        g.drawLine(50,200,50,400);
        yCor+=5;

    }

	public void setStationList(ArrayList<Station> stationList) {
		this.stationList = stationList;
	}

	public ArrayList<Station> getStationList() {
		return stationList;
	}


}
