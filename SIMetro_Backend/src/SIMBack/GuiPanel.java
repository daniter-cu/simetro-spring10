package SIMBack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GuiPanel extends Panel {
    BufferedImage imageTrain;
    BufferedImage imageStation1;
    BufferedImage imageStation2;
    BufferedImage imageStation3;
    BufferedImage imageStation4;
    int xCor = 50;
    int yCor = 0;

    public GuiPanel() {
        try {
            String imageName = "src/SIMBack/resources/Train.jpg";
            String imageName2 = "src/SIMBack/resources/Station.jpg";
            File input = new File(imageName);
            imageTrain = ImageIO.read(input);
            File input2 = new File(imageName2);
            imageStation1 = ImageIO.read(input2);
            imageStation2 = ImageIO.read(input2);
            imageStation3 = ImageIO.read(input2);
            imageStation4 = ImageIO.read(input2);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
    }

    public void paint(Graphics g) {
        g.drawImage(imageTrain, xCor, yCor, null);
        g.drawImage(imageStation1, 50, 0, null);
        g.drawImage(imageStation2, 50, 200, null);
        g.drawImage(imageStation3, 50, 400, null);
        g.drawImage(imageStation4, 450, 200, null);
        yCor++;

    }


}
