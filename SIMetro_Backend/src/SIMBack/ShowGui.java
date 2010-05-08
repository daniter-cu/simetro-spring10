package SIMBack;

import java.awt.Color;
import java.awt.Panel;
import javax.swing.JFrame;

public class ShowGui {
	
	public void Show() {
		try {
	        JFrame frame = new JFrame("Display image");
	        Panel panel = new GuiPanel();
	        panel.setBackground(Color.white);
	        frame.getContentPane().add(panel);
	        frame.setSize(700, 500);
	        frame.setVisible(true);
	        while (true) {
	            panel.repaint();
	            try {
	                Thread.sleep(10);
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
