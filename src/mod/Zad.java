package mod;

import mod.neighbours.*;

import java.awt.*;
import java.util.*;

public class Zad {
    int val0;
    public int height, width, Z,seed, maxCount = 100;
    boolean isFlow;
    public int[][][] tab; //value of rgb
    public boolean visualizeEnergies = false;
    public boolean visualizeBabies = false;
    int[][][] energies;
    double[][][] densities;
    public ArrayList<Integer> colors = new ArrayList<Integer>();
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
        neighbourAlgorithm = new VonNeumanNeighbour(width, height, Z, isFlow, seed);
        monteCarlo = new MonteCarlo(neighbourAlgorithm, width, height,Z);
        val0=NeighbourAlgorithm.neutralColor;
        ClearTabs();
    }

    private void ClearTabs() {
        tab = new int[height][width][Z];
        energies = new int[height][width][Z];
        densities = new double[height][width][Z];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < Z; k++) {
                    tab[j][i][k] = val0;
                }
            }
        }
        colorIndex = 0;
        neighbourAlgorithm.isPeriodic = isFlow;
        colors.clear();
        monteCarlo = new MonteCarlo(neighbourAlgorithm, width, height,Z);
    }

    void createColoredBud(int x, int y, int z, int color) {
        int col = color;
        colors.add(col);
        tab[y][x][z] = col;
    }

    public void generateRandom(int n) {
        ClearTabs();
        seed=n;
        n = Math.min(n, width * height*Z);
        int i = 0;
        while (i < n) {
            int randX = rand.nextInt(width);
            int randY = rand.nextInt(height);
            int randZ = rand.nextInt(Z);
            createColoredBud(randX, randY, randZ, i + 1);
            i++;
        }
    }

    public void changeAlgorithm(int alg) {
        switch (alg) {
            case 0:
                neighbourAlgorithm = new VonNeumanNeighbour(width, height, Z, isFlow,seed);
                break;
            case 1:
                neighbourAlgorithm = new MooreNeighbour(width, height,Z, isFlow,seed);
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