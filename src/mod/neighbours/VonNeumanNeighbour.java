package mod.neighbours;

import java.awt.*;
import java.util.*;
import java.util.List;

public class VonNeumanNeighbour extends NeighbourAlgorithm {

    public VonNeumanNeighbour(int w, int h, int z, boolean isPeriod, int seeds) {
        width = w;
        height = h;
        Z = z;
        isPeriodic = isPeriod;
        seedsCount = seeds;
    }

    @Override
    protected int decideCell(int x, int y, int z) {
        int[] neighbours = new int[6];
        int j = 0;
        if (isPeriodic) {
            neighbours[j++] = colorsTab[(y - 1 + height) % height][x][z];
            neighbours[j++] = colorsTab[(y + 1) % height][x][z];
            neighbours[j++] = colorsTab[y][(x - 1 + width) % width][z];
            neighbours[j++] = colorsTab[y][(x + 1) % width][z];
            neighbours[j++] = colorsTab[y][x][(z + 1) % Z];
            neighbours[j++] = colorsTab[y][x][(Z - 1 + Z) % Z];
        } else {
            neighbours[j++] = (y - 1 < 0) ? neutralColor : colorsTab[y - 1][x][z];
            neighbours[j++] = (y + 1 < height) ? colorsTab[y + 1][x][z] : neutralColor;
            neighbours[j++] = (x - 1 < 0) ? neutralColor : colorsTab[y][x - 1][z];
            neighbours[j++] = (x + 1 < width) ? colorsTab[y][x + 1][z] : neutralColor;
            neighbours[j++] = (z - 1 < 0) ? neutralColor : colorsTab[y][x][z - 1];
            neighbours[j++] = (z + 1 < Z) ? colorsTab[y][x][z + 1] : neutralColor;
        }
        Map<Integer, Integer> neighboursNumbers = new HashMap();
        for (int neighbour : neighbours) {
            if (neighboursNumbers.containsKey(neighbour) == false){
                neighboursNumbers.put(neighbour, 0);
            }
            int count = neighboursNumbers.get(neighbour) + 1;
            neighboursNumbers.put(neighbour, count);
        }

        return getIndexOfMax(neighboursNumbers);
    }

    @Override
    public int[] getNeighboursColors(int x, int y, int z, int myColor) {
        Set<Integer> colorSet = new HashSet<>();
        if (isPeriodic) {
            if (isEdge(x, (y + 1) % height,z, myColor) && myColor!=colorsTab[(y + 1) % height][x][z])
                colorSet.add(colorsTab[(y + 1) % height][x][z]);
            if (isEdge((x + 1) % width, y,z, myColor) && myColor!=colorsTab[y][(x + 1) % width][z])
                colorSet.add(colorsTab[y][(x + 1) % width][z]);
            if (isEdge(x, y, (z + 1) % Z, myColor) && myColor!=colorsTab[y][x][(z + 1) % Z])
                colorSet.add(colorsTab[y][x][(z + 1) % Z]);
            if (isEdge(x, (y - 1 + height) % height,z, myColor) && myColor!=colorsTab[(y - 1 + height) % height][x][z])
                colorSet.add(colorsTab[(y - 1 + height) % height][x][z]);
            if (isEdge((x - 1 + width) % width, y,z, myColor) && myColor!=colorsTab[y][(x - 1 + width) % width][z])
                colorSet.add(colorsTab[y][(x - 1 + width) % width][z]);
            if (isEdge(x, y, (z - 1 + Z) % Z, myColor) && myColor!=colorsTab[y][x][(z - 1 + Z) % Z])
                colorSet.add(colorsTab[y][x][(z - 1 + Z) % Z]);
        } else {
            if ((y + 1 < height) && isEdge(x, y + 1, z, myColor) && myColor!=colorsTab[y + 1][x][z])
                colorSet.add(colorsTab[y + 1][x][z]);
            if ((x + 1 < width) && isEdge(x + 1, y, z, myColor) && myColor!=colorsTab[y][x + 1][z])
                colorSet.add(colorsTab[y][x + 1][z]);
            if ((z + 1 < Z) && isEdge(x, y, z + 1, myColor) && myColor!=colorsTab[y][x][z + 1])
                colorSet.add(colorsTab[y][x][z + 1]);
            if ((y - 1 >= 0) && isEdge(x, y - 1, z, myColor) && myColor!=colorsTab[y - 1][x][z])
                colorSet.add(colorsTab[y - 1][x][z]);
            if ((x - 1 >= 0) && isEdge(x - 1, y, z, myColor) && myColor!=colorsTab[y][x - 1][z])
                colorSet.add(colorsTab[y][x - 1][z]);
            if ((z - 1 >= 0) && isEdge(x, y, z - 1, myColor) && myColor!=colorsTab[y][x][z - 1])
                colorSet.add(colorsTab[y][x][z - 1]);
        }
        return new int[0];
    }


    @Override
    public int getEnergy(int x, int y, int z, int color) {
        int energy = 0;
      int myRealColor = colorsTab[y][x][z];
        colorsTab[y][x][z] = color;
        if (isPeriodic) {
            if (isEdge(x, (y + 1) % height, z))
                energy++;
            if (isEdge((x + 1) % width, y, z))
                energy++;
            if (isEdge(x, y, (z + 1) % Z))
                energy++;
            if (isEdge(x, (y - 1 + height) % height, z))
                energy++;
            if (isEdge((x - 1 + width) % width, y, z))
                energy++;
            if (isEdge(x, y, (z - 1 + Z) % Z))
                energy++;
        } else {
            if ((y + 1 < height) && isEdge(x, y + 1, z))
                energy++;
            if ((x + 1 < width) && isEdge(x + 1, y, z))
                energy++;
            if ((z + 1 < Z) && isEdge(x, y, z + 1))
                energy++;
            if ((y - 1 >= 0) && isEdge(x, y - 1, z))
                energy++;
            if ((x - 1 >= 0) && isEdge(x - 1, y, z))
                energy++;
            if ((z - 1 >= 0) && isEdge(x, y, z - 1))
                energy++;
        }
        colorsTab[y][x][z] = myRealColor;
        return energy;
    }

    @Override
    public boolean isEdge(int x, int y, int z, int color) {
      int thisColor = color;
        if (isPeriodic) {
            if (thisColor!=colorsTab[(y + 1) % height][x][z])
                return true;
            if (thisColor!=colorsTab[y][(x + 1) % width][z])
                return true;
            if (thisColor!=colorsTab[y][x][(z + 1) % Z])
                return true;
            if (thisColor!=colorsTab[(y - 1 + height) % height][x][z])
                return true;
            if (thisColor!=colorsTab[y][(x - 1 + width) % width][z])
                return true;
            return thisColor!=colorsTab[y][x][(z - 1 + Z) % Z];
        } else {
            if ((y + 1 < height) && thisColor!=colorsTab[y + 1][x][z])
                return true;
            if ((x + 1 < width) && thisColor!=colorsTab[y][x + 1][z])
                return true;
            if ((z + 1 < Z) && thisColor!=colorsTab[y][x][z + 1])
                return true;
            if ((y - 1 >= 0) && thisColor!=colorsTab[y - 1][x][z])
                return true;
            if ((x - 1 >= 0) && thisColor!=colorsTab[y][x - 1][z])
                return true;
            return (z - 1 >= 0) && thisColor!=colorsTab[y][x][z - 1];
        }
    }

    @Override
    public Integer[][][] getNeighbours(int x, int y,int z) {
        List<Integer[]> neighbours = new ArrayList<>();
        if (isPeriodic) {
            neighbours.add(new Integer[]{x, (y + 1) % height,z});
            neighbours.add(new Integer[]{(x + 1) % width, y,z});
            neighbours.add(new Integer[]{x, y,(z + 1) %Z});
            neighbours.add(new Integer[]{x, (y - 1 + height) % height,z});
            neighbours.add(new Integer[]{(x - 1 + width) % width, y,z});
            neighbours.add(new Integer[]{x, y,(z - 1 + Z) % Z});
        } else {
            if ((y + 1 < height))
                neighbours.add(new Integer[]{x, y + 1,z});
            if ((x + 1 < width))
                neighbours.add(new Integer[]{x + 1, y,z});
            if ((z + 1 < Z))
                neighbours.add(new Integer[]{x, y,z+1});
            if ((y - 1 >= 0))
                neighbours.add(new Integer[]{x, y - 1,z});
            if ((x - 1 >= 0))
                neighbours.add(new Integer[]{x - 1, y,z});
            if ((z - 1 >= 0))
                neighbours.add(new Integer[]{x, y,z-1});
        }
        return neighbours.toArray(new Integer[neighbours.size()][][]);
    }
}
