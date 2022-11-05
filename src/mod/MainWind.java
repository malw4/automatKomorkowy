package mod;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainWind extends JFrame {

    int initialWidth, initialHeight, Z;
    int a, alg, period, mcsteps;
    double kt;

    public MainWind(String title) throws IOException {
        super(title);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(("im.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File myObj = new File("parameters.txt");
            Scanner myReader = new Scanner(myObj);
            initialWidth = (Integer.parseInt(myReader.nextLine()));
            initialHeight = (Integer.parseInt(myReader.nextLine()));
            Z = (Integer.parseInt(myReader.nextLine()));
            a = (Integer.parseInt(myReader.nextLine()));
            alg = (Integer.parseInt(myReader.nextLine()));
            period = (Integer.parseInt(myReader.nextLine()));
            kt = (Double.parseDouble(myReader.nextLine()));
            mcsteps = (Integer.parseInt(myReader.nextLine()));
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        JPane canvas = new JPane(image, initialWidth, initialHeight, Z);

        // =============================================================================================================

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, canvas);

        JPanel generalPanel = new JPanel();
        generalPanel.setPreferredSize(new Dimension(180, 180));

        mainPanel.add(BorderLayout.EAST, generalPanel);

        //==============================================================================================================

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(200, 200));
        this.setLocationRelativeTo(null);
        setPreferredSize(new Dimension(200, 180));
        canvas.zad.generateRandom(a);
        canvas.setAlgorithm(alg);

        canvas.setPeriodic(period != 0);
        System.out.println("Start generating");
        // repaint();
        canvas.czyCzasPlynie = true;
        canvas.millisActualTime = System.currentTimeMillis();
        canvas.zad.kt = kt;
        canvas.zad.remainingMCSteps = mcsteps;
        // canvas.stepper(initialHeight,initialWidth);
    }

    public static void main(String[] args) throws IOException {
        MainWind window = new MainWind("ZIARNA");
        window.setVisible(true);
    }

}