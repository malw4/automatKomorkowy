package mod.neighbours;

import java.util.ArrayList;
import java.util.Random;

public class MonteCarlo {
    int width, height, Z;
    public NeighbourAlgorithm neighbourAlgorithm;
    ArrayList<Integer> randX = new ArrayList<>();
    ArrayList<Integer> randY = new ArrayList<>();
    ArrayList<Integer> randZ = new ArrayList<>();
    public boolean[][][] edges;
    Random rand = new Random();

    public MonteCarlo(NeighbourAlgorithm neigh, int width, int height, int Z) {
        this.width = width;
        this.height = height;
        this.Z = Z;
        neighbourAlgorithm = neigh;
    }

    public void makeEdgesTab(int[][][] tab) {
        edges = neighbourAlgorithm.getEdges(tab);
    }

    public int[][][] doMonteCarlo(int[][][] tab, double kt) {
        int[][][] newTab = new int[height][width][Z];
        randX.clear();
        randY.clear();
        randZ.clear();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < Z; k++) {
                    newTab[j][i][k] = tab[j][i][k];
                    randX.add(i);
                    randY.add(j);
                    randZ.add(k);
                }
            }
        }
        while (!randX.isEmpty()) {
            int randomIndex = rand.nextInt(randX.size());
            int x = randX.get(randomIndex);
            int y = randY.get(randomIndex);
            int z = randZ.get(randomIndex);
            randX.remove(randomIndex);
            randY.remove(randomIndex);
            randZ.remove(randomIndex);
            if (edges[y][x][z]) {
                int energyNOW = calculateEnergy(x, y, z, tab[y][x][z]);
                int bestColor = tab[y][x][z];
                int lowestEnergy = energyNOW;
                int[] nc = neighbourAlgorithm.getNeighboursColors(x, y, z, tab[y][x][z]);
                if (nc.length > 0) {
                    int col = nc[rand.nextInt(nc.length)];
                    int hypotheticalEnergy = calculateEnergy(x, y, z, col);
                    if (hypotheticalEnergy <= lowestEnergy) {
                        bestColor = col;
                    } else {
                        double p = Math.exp((-1 * (hypotheticalEnergy - energyNOW)) / kt);
                        if (rand.nextDouble() <= p)
                            bestColor = col;
                    }
                    tab[y][x][z] = bestColor;
                }
            }
        }
        return tab;
    }

    private int calculateEnergy(int x, int y, int z, int col) {
        return neighbourAlgorithm.getEnergy(x, y, z, col);
    }
}
