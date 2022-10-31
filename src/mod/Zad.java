package mod;

import mod.neighbours.*;

import java.awt.*;
import java.util.*;

public class Zad {
    public int height, width, Z, maxCount = 100;
    boolean isFlow;
    public Color[][][] tab;

    public boolean visualizeEnergies = false;
    public boolean visualizeBabies = false;
    int[][][] energies;
    double[][][] densities;
    public ArrayList<Color> colors = new ArrayList<Color>();
    Random rand = new Random();
    int colorIndex = 0;
    public NeighbourAlgorithm neighbourAlgorithm;

    MonteCarlo monteCarlo;
    public double kt = 0.1;
    public int remainingMCSteps = 0;
    Zad(int w, int h, int z) {
        width = w;
        height = h;
        Z = z;
        neighbourAlgorithm = new VonNeumanNeighbour(width, height, Z, isFlow);
        monteCarlo = new MonteCarlo(neighbourAlgorithm, width, height,Z);
        ClearTabs();
    }

    private void ClearTabs() {
        tab = new Color[height][width][Z];
        energies = new int[height][width][Z];
        densities = new double[height][width][Z];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < Z; k++) {
                    tab[j][i][k] = NeighbourAlgorithm.neutralColor;
                }
            }
        }
        colorIndex = 0;
        neighbourAlgorithm.isPeriodic = isFlow;
        colors.clear();

        monteCarlo = new MonteCarlo(neighbourAlgorithm, width, height,Z);
    }

    void createColoredBud(int x, int y, int z) {
        Color col;
        do {
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);
            col = new Color(r, g, b);
        } while (colors.indexOf(col) >= 0 || col.equals(NeighbourAlgorithm.neutralColor));
        colors.add(col);
        tab[y][x][z] = col;
    }

    public void generateRandom(int n) {
        ClearTabs();
        n = Math.min(n, width * height*Z);
        int i = 0;
        while (i < n) {
            int randX = rand.nextInt(width);
            int randY = rand.nextInt(height);
            int randZ = rand.nextInt(Z);
            createColoredBud(randX, randY, randZ);
            i++;
        }
    }

    public void changeAlgorithm(int alg) {
        switch (alg) {
            case 0:
                neighbourAlgorithm = new VonNeumanNeighbour(width, height, Z, isFlow);
                break;
            case 1:
                neighbourAlgorithm = new MooreNeighbour(width, height,Z, isFlow);
                break;
        }
        //monteCarlo.neighbourAlgorithm = neighbourAlgorithm;
    }

    public void stepNeighbour() {
        tab = neighbourAlgorithm.getNextTab(tab);
    }

    public void makeEdgesTab() {
        monteCarlo.makeEdgesTab(tab);
    }

    public void doMonteCarlo() {
        neighbourAlgorithm.colorsTab = tab;
        monteCarlo.makeEdgesTab(tab);
        tab = monteCarlo.doMonteCarlo(tab, kt);
    }
}