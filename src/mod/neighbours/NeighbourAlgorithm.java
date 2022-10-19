package mod.neighbours;

import java.awt.*;

public abstract class NeighbourAlgorithm {

    public boolean isPeriodic;
    public Color[] possibleColors;
    public static Color neutralColor = Color.white;
    public Color[][][] colorsTab;
    protected int width, height, Z;


    public int getIndexOfMax(int[] arr) throws Exception {
        int sum = 0;
        int maxAt = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (arr[i] > arr[maxAt])
                maxAt = i;
        }
        if (sum == 0)
            throw new Exception();
        return maxAt;
    }

    public Color[][][] getNextTab(Color[][][] tab) {
        colorsTab = tab;
        height = colorsTab.length;
        width = colorsTab[0].length;
        Z = colorsTab[0][0].length;
        Color[][][] newTab = new Color[height][width][Z];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < Z; k++) {
                    newTab[j][i][k] = colorsTab[j][i][k];
                }
            }
        }
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < Z; k++) {
                    if (newTab[j][i][k].equals(neutralColor)) {
                        newTab[j][i][k] = decideCell(i, j, k);
                    }
                }
            }
        }
        return newTab;
    }

    public boolean[][][] getEdges(Color[][][] tab) {
        colorsTab = tab;
        height = tab.length;
        width = tab[0].length;
        Z = tab[0][0].length;
        boolean[][][] edgesTab = new boolean[height][width][Z];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < Z; k++) {
                    edgesTab[j][i][k] = isEdge(i, j, k);
                }
            }
        }
        return edgesTab;
    }

    public boolean isEdge(int x, int y, int z) {
        return isEdge(x, y, z, colorsTab[y][x][z]);
    }

    // returns count of neighbours with color different than color passed in argument
    public abstract int getEnergy(int x, int y,int z, Color color);

    // checks if there are neighbours with color different than color passed in argument
    public abstract boolean isEdge(int x, int y,int z, Color color);

    // returns color which most of neighbours have (but not neutral)
    protected abstract Color decideCell(int x, int y,int z);

    // returns array of colors of all neighbours (without repetitions)
    public abstract Color[] getNeighboursColors(int x, int y,int z, Color myColor);

    public abstract Integer[][][] getNeighbours(int x, int y,int z);
}
