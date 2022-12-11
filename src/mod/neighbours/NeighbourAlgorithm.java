package mod.neighbours;

import java.awt.*;
import java.util.Map;

public abstract class NeighbourAlgorithm {

    public boolean isPeriodic;
    public static int neutralColor = 0;
    public int[][][] colorsTab;
    protected int width, height, Z;
    protected int seedsCount;

//    public int getIndexOfMax(int[] arr) throws Exception {
//        int sum = 0;
//        int maxAt = 0;
//        int length=arr.length;
//        for (int i = 0; i < length; i++) {
//            sum += arr[i];
//            if (arr[i] > arr[maxAt])
//                maxAt = i;
//        }
//        if (sum == 0)
//            throw new Exception();
//        return maxAt;
//    }

    public int getIndexOfMax(Map<Integer, Integer> arr) {
        int max = 0;
        int maxAt = 0;

        for (var mapEntry : arr.entrySet()) {
            int color = mapEntry.getKey();
            int count = mapEntry.getValue();
            if (color != 0 && count > max) {
                max = count;
                maxAt = color;
            }
        }
        return maxAt;

    }

    public int[][][] getNextTab(int[][][] tab) {
        int empty = 0;
        colorsTab = tab;
        height = colorsTab.length;
        width = colorsTab[0].length;
        Z = colorsTab[0][0].length;
        int[][][] newTab = new int[height][width][Z];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                for (int k = 0; k < Z; k++) {
                    newTab[j][i][k] = colorsTab[j][i][k];
                    if (newTab[j][i][k] == (neutralColor)) {
                        newTab[j][i][k] = decideCell(i, j, k);
                    }
                    if (newTab[j][i][k] == (neutralColor)) {
                        empty++;
                    }
                }
            }
        }
        System.out.println("Zostało pustych: "+empty);
        return newTab;
    }

    public boolean[][][] getEdges(int[][][] tab) {
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
    public abstract int getEnergy(int x, int y, int z, int color);

    // checks if there are neighbours with color different than color passed in argument
    public abstract boolean isEdge(int x, int y, int z, int color);

    // returns color which most of neighbours have (but not neutral)
    protected abstract int decideCell(int x, int y, int z);

    // returns array of colors of all neighbours (without repetitions)
    public abstract int[] getNeighboursColors(int x, int y, int z, int myColor);

    public abstract Integer[][][] getNeighbours(int x, int y, int z);
}
