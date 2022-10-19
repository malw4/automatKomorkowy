package mod;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainWind extends JFrame {
    Scanner scan = new Scanner(System.in);
    private JPane canvas;

    int initialWidth = 180, initialHeight = 150;
    private JPanel mainPanel;
    private JPanel generalPanel;

    public MainWind(String title) throws IOException {
        super(title);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(("im.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        canvas = new JPane(image, initialWidth, initialHeight);

        // =============================================================================================================

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, canvas);

        generalPanel = new JPanel();
        generalPanel.setPreferredSize(new Dimension(180, 180));

        mainPanel.add(BorderLayout.EAST, generalPanel);

        //==============================================================================================================

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(200, 200));
        this.setLocationRelativeTo(null);
        setPreferredSize(new Dimension(200, 180));
        int a = 0;
        canvas.Z=20;
        System.out.println("Choose number of generated grains:");
        String liczb=scan.nextLine();
        a = (Integer.parseInt(liczb));
        canvas.zad.generateRandom(a);
        System.out.println("Choose way to generate neighbours: Neumann - select 0, Moore - select 1");
        String neighbourWay=scan.nextLine();
        int alg=(Integer.parseInt(neighbourWay));
        canvas.setAlgorithm(alg);
        System.out.println("Do you want periodic - select 1, non-periodic - select 0");
        int period=(Integer.parseInt(scan.nextLine()));
        if(period==0)
            canvas.setPeriodic(false);
        else
            canvas.setPeriodic(true);
        System.out.println("Start generating");
       // repaint();
        canvas.czyCzasPlynie = true;
        canvas.millisActualTime = System.currentTimeMillis();
        canvas.zad.neighbourAlgorithm.possibleColors = canvas.zad.colors.toArray(new Color[0]);

        //canvas.stepper(initialHeight,initialWidth);
    }

    public static void main(String[] args) throws IOException {
        MainWind window = new MainWind("ZIARNA");
        window.setVisible(true);
    }
}