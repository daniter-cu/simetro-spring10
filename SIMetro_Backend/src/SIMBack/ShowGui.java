package SIMBack;

import java.awt.Color;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ShowGui {

	public void Show(ArrayList<Station> stationl,ArrayList<Line> linel) {
		try {
			JFrame frame = new JFrame("Display image");
			GuiPanel panel = new GuiPanel(stationl,linel);
			panel.setBackground(Color.white);
			frame.getContentPane().add(panel);
			frame.setSize(1000, 800);
			frame.setVisible(true);
			JScrollPane scrollBar=new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);   
			frame.add(scrollBar);  
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

			while (true) {
				panel.repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
}
