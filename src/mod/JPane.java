package mod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JPane extends JPanel implements ActionListener {
    Scanner scan = new Scanner(System.in);
    int whiteRgb = Color.WHITE.getRGB();
    public int cellSize;
    private BufferedImage image;
    public Zad zad;
    int change, war2 = 0, fulfillness = 0, full = 0;
    String mc = "no";
    Timer timer = new Timer(400, this);
    public boolean czyCzasPlynie = true;
    public boolean periodic;
    public int algorithmType;
    BufferedWriter writer;
    BufferedWriter MCwrite;
    BufferedWriter Json;
    public long millisActualTime;
    double millis;

    public JPane(BufferedImage image, int initialWidth, int initialHeight, int Z) throws IOException {
        millisActualTime = System.currentTimeMillis();
        this.image = image;
        setNewZad(initialWidth, initialHeight, Z);
        repaint();
        startTime();
        millis = System.currentTimeMillis() - millisActualTime;
        System.out.printf("Time to generate xyz: " + millis + " ms");
        System.out.println();
        writer = new BufferedWriter(new FileWriter("generated.txt"));
        MCwrite = new BufferedWriter(new FileWriter("MCgenerated.txt"));
        Json = new BufferedWriter(new FileWriter("Times.json"));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (zad.neighbourAlgorithm.possibleColors == null)
            zad.neighbourAlgorithm.possibleColors = zad.colors.toArray(new Color[0]);
        if (e.getSource() == timer) {
            if (czyCzasPlynie) {
                zad.stepNeighbour();
            } else if (zad.remainingMCSteps > 0) {
                zad.doMonteCarlo();
                zad.remainingMCSteps--;
                if (zad.remainingMCSteps == 0)
                    mc = "yes";
            }
            repaint();
        }
    }

    public void setNewZad(int width, int height, int z) {
        zad = new Zad(width, height, z);
        zad.isFlow = periodic;
        zad.changeAlgorithm(algorithmType);
        setCellSize();
        repaint();
    }

    public void startTime() {
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        for (int j = 0; j < zad.height; j++) {
            for (int i = 0; i < zad.width; i++) {
                for (int k = 0; k < zad.Z; k++) {

                    if (zad.tab[j][i][k].equals(Color.WHITE)) {
                        fulfillness++;
                    }
                }
            }
        }
        if (fulfillness == 0 && full == 0) {
            double executionTime = System.currentTimeMillis() - millisActualTime;
            System.out.println("Timer value:  " + executionTime / 1000 + "s");
            millisActualTime = System.currentTimeMillis();
            for (int j = 0; j < zad.height; j++) {
                for (int i = 0; i < zad.width; i++) {
                    for (int k = 0; k < zad.Z; k++) {
                        try {
                            writer.write(i + " " + j + " " + k + " " + zad.tab[j][i][k]);
                            writer.newLine();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
            try {

                double writeTime = System.currentTimeMillis() - millisActualTime;
                System.out.println(".txt file have been generated");
                System.out.println("Time to write file: " + writeTime + " ms");
                writer.close();
                czyCzasPlynie = false;
                Json.write("Time to generate xyz: " + millis + " ms");
                Json.newLine();
                Json.write("Timer value:  " + executionTime / 1000 + "s");
                Json.newLine();
                Json.write("Time to write file: " + writeTime + " ms");
                Json.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            full++;
        } else if (full == 0) {
            fulfillness = 0;
        }
        if (full == 1 && mc.equals("yes")) {
            for (int j = 0; j < zad.height; j++) {
                for (int i = 0; i < zad.width; i++) {
                    for (int k = 0; k < zad.Z; k++) {
                        try {
                            MCwrite.write(i + " " + j + " " + k + " " + zad.tab[j][i][k]);
                            MCwrite.newLine();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }
            try {
                MCwrite.close();
                System.out.println("MC.txt file have been generated");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            mc = "no";
            full++;
        }

    }

    void setCellSize() {
        int max = Math.max(zad.width, zad.height);
        if (max < 1000)
            cellSize = (int) Math.ceil(1000 / max);
        else
            cellSize = 1;
    }

    public void setPeriodic(boolean period) {
        periodic = period;
        zad.isFlow = periodic;
        zad.neighbourAlgorithm.isPeriodic = periodic;
    }

    public void applyReg() {
        if (change >= 0 && change <= 250) {
            if (war2 == 1)
                applyRegPeriodic(image, change);
            else
                image = applyReg(image, change);
        }
        repaint();
    }

    public void repaint() {
        super.repaint();
    }

    public BufferedImage applyReg(BufferedImage image, int reg) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int j = 1; j < height; j++) {
            for (int i = 1; i < width - 1; i++) {
                boolean left, center, right;
                left = (image.getRGB(i - 1, j - 1) & 0xff) < 128;
                center = (image.getRGB(i, j - 1) & 0xff) < 128;
                right = (image.getRGB(i + 1, j - 1) & 0xff) < 128;
                int sum = 0;
                if (left) sum += 4;
                if (center) sum += 2;
                if (right) sum += 1;
                image.setRGB(i, j, whiteRgb);
            }
        }
        return image;
    }

    public void applyRegPeriodic(BufferedImage image, int reg) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int j = 1; j < height; j++) {
            for (int i = 0; i < width; i++) {
                boolean left, center, right;
                int leftX = i - 1;
                if (leftX < 0) leftX += width;
                int rightX = i + 1;
                if (rightX >= width) rightX -= width;
                left = (image.getRGB(leftX, j - 1) & 0xff) < 128;
                center = (image.getRGB(i, j - 1) & 0xff) < 128;
                right = (image.getRGB(rightX, j - 1) & 0xff) < 128;
                int sum = 0;
                if (left) sum += 4;
                if (center) sum += 2;
                if (right) sum += 1;
                image.setRGB(i, j, whiteRgb);
            }
        }
    }

    public void setAlgorithm(int alg) {
        algorithmType = alg;
        zad.changeAlgorithm(alg);
    }
}