package mod.neighbours;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MooreNeighbour extends NeighbourAlgorithm {

    public MooreNeighbour(int w, int h, int z, boolean isPeriod) {
        width = w;
        height = h;
        Z = z;
        isPeriodic = isPeriod;
    }

    @Override
    protected Color decideCell(int x, int y, int z) {
        Color[] neighbours = new Color[14];
        int j = 0;
        if (isPeriodic) {
            neighbours[j++] = colorsTab[y][x][(z + 1) % Z];
            neighbours[j++] = colorsTab[y][x][(z - 1 + Z) % Z];
            neighbours[j++] = colorsTab[(y + 1) % height][x][z];
            neighbours[j++] = colorsTab[(y + 1) % height][x][(z + 1) % Z];
            neighbours[j++] = colorsTab[(y + 1) % height][x][(z - 1 + Z) % Z];
            neighbours[j++] = colorsTab[(y + 1) % height][(x + 1) % width][z];
            neighbours[j++] = colorsTab[y][(x + 1) % width][z];
            neighbours[j++] = colorsTab[(y - 1 + height) % height][(x + 1) % width][z];
            neighbours[j++] = colorsTab[(y - 1 + height) % height][x][z];
            neighbours[j++] = colorsTab[(y - 1 + height) % height][x][(z + 1) % Z];
            neighbours[j++] = colorsTab[(y - 1 + height) % height][x][(z - 1 + Z) % Z];
            neighbours[j++] = colorsTab[(y - 1 + height) % height][(x - 1 + width) % width][z];
            neighbours[j++] = colorsTab[y][(x - 1 + width) % width][z];
            neighbours[j++] = colorsTab[(y + 1) % height][(x - 1 + width) % width][z];
        } else {
            neighbours[j++] = (z + 1 < Z) ? colorsTab[y][x][z + 1] : neutralColor;
            neighbours[j++] = (z - 1 >= 0) ? colorsTab[y][x][z - 1] : neutralColor;
            neighbours[j++] = (y + 1 < height) && (z + 1 < Z) ? colorsTab[y + 1][x][z + 1] : neutralColor;
            neighbours[j++] = (y + 1 < height) ? colorsTab[y + 1][x][z] : neutralColor;
            neighbours[j++] = (y + 1 < height) && (z - 1 >= 0) ? colorsTab[y + 1][x][z - 1] : neutralColor;
            neighbours[j++] = (y + 1 < height && x + 1 < width) ? colorsTab[y + 1][x + 1][z] : neutralColor;
            neighbours[j++] = (x + 1 < width) ? colorsTab[y][x + 1][z] : neutralColor;
            neighbours[j++] = (y - 1 >= 0 && x + 1 < width) ? colorsTab[y - 1][x + 1][z] : neutralColor;
            neighbours[j++] = (y - 1 >= 0) && (z + 1 < Z) ? colorsTab[y - 1][x][z + 1] : neutralColor;
            neighbours[j++] = (y - 1 >= 0) ? colorsTab[y - 1][x][z] : neutralColor;
            neighbours[j++] = (y - 1 >= 0) && (z - 1 >= 0) ? colorsTab[y - 1][x][z - 1] : neutralColor;
            neighbours[j++] = (y - 1 >= 0 && x - 1 >= 0) ? colorsTab[y - 1][x - 1][z] : neutralColor;
            neighbours[j++] = (x - 1 >= 0) ? colorsTab[y][x - 1][z] : neutralColor;
            neighbours[j++] = (y + 1 < height && x - 1 >= 0) ? colorsTab[y + 1][x - 1][z] : neutralColor;
        }

        int[] colorCounts = new int[possibleColors.length];
        for (Color neighbour : neighbours) {
            for (int i = 0; i < possibleColors.length; i++) {
                if (neighbour.equals(possibleColors[i])) {
                    colorCounts[i]++;
                    break;
                }
            }
        }
        try {
            int maxIndex = getIndexOfMax(colorCounts);
            return possibleColors[maxIndex];
        } catch (Exception e) {
            return neutralColor;
        }
    }

    @Override
    public Color[] getNeighboursColors(int x, int y, int z, Color myColor) {
        Set<Color> colorSet = new HashSet<>();
        if (isPeriodic) {
            if (isEdge(x, y, z, myColor) && myColor.equals(colorsTab[y][x][z]))
                colorSet.add(colorsTab[y][x][z]);
            if (isEdge(x, y, (z + 1) % Z, myColor) && myColor.equals(colorsTab[y][x][(z + 1) % Z]))
                colorSet.add(colorsTab[y][x][(z + 1) % Z]);
            if (isEdge(x, y, (z - 1 + Z) % Z, myColor) && myColor.equals(colorsTab[y][x][(z - 1 + Z) % Z]))
                colorSet.add(colorsTab[y][x][(z - 1 + Z) % Z]);
            if (isEdge(x, (y + 1) % height, (z + 1) % Z, myColor) && myColor.equals(colorsTab[(y + 1) % height][x][(z + 1) % Z]))
                colorSet.add(colorsTab[(y + 1) % height][x][(z + 1) % Z]);
            if (isEdge(x, (y + 1) % height, (z - 1 + Z) % Z, myColor) && myColor.equals(colorsTab[(y + 1) % height][x][(z - 1 + Z) % Z]))
                colorSet.add(colorsTab[(y + 1) % height][x][(z - 1 + Z) % Z]);
            if (isEdge((x + 1) % width, (y + 1) % height, z, myColor) && myColor.equals(colorsTab[(y + 1) % height][(x + 1) % width][z]))
                colorSet.add(colorsTab[(y + 1) % height][(x + 1) % width][z]);
            if (isEdge((x + 1) % width, y, z, myColor) && myColor.equals(colorsTab[y][(x + 1) % width][z]))
                colorSet.add(colorsTab[y][(x + 1) % width][z]);
            if (isEdge((x + 1) % width, (y - 1 + height) % height, z, myColor) && myColor.equals(colorsTab[(y - 1 + height) % height][(x + 1) % width][z]))
                colorSet.add(colorsTab[(y - 1 + height) % height][(x + 1) % width][z]);
            if (isEdge(x, (y - 1 + height) % height, (z + 1) % Z, myColor) && myColor.equals(colorsTab[(y - 1 + height) % height][x][(z + 1) % Z]))
                colorSet.add(colorsTab[(y - 1 + height) % height][x][(z + 1) % Z]);
            if (isEdge(x, (y - 1 + height) % height, (z - 1 + Z) % Z, myColor) && myColor.equals(colorsTab[(y - 1 + height) % height][x][(z - 1 + Z) % Z]))
                colorSet.add(colorsTab[(y - 1 + height) % height][x][(z - 1 + Z) % Z]);
            if (isEdge(x, (y - 1 + height) % height, z, myColor) && myColor.equals(colorsTab[(y - 1 + height) % height][x][z]))
                colorSet.add(colorsTab[(y - 1 + height) % height][x][z]);
            if (isEdge((x - 1 + width) % width, (y - 1 + height) % height, z, myColor) && myColor.equals(colorsTab[(y - 1 + height) % height][(x - 1 + width) % width][z]))
                colorSet.add(colorsTab[(y - 1 + height) % height][(x - 1 + width) % width][z]);
            if (isEdge((x - 1 + width) % width, y, z, myColor) && myColor.equals(colorsTab[y][(x - 1 + width) % width][z]))
                colorSet.add(colorsTab[y][(x - 1 + width) % width][z]);
            if (isEdge((x - 1 + width) % width, (y + 1) % height, z, myColor) && myColor.equals(colorsTab[(y + 1) % height][(x - 1 + width) % width][z]))
                colorSet.add(colorsTab[(y + 1) % height][(x - 1 + width) % width][z]);
        } else {
            if ((y + 1 < height) && (z + 1 < Z) && isEdge(x, y, z + 1, myColor) && myColor.equals(colorsTab[y][x][z + 1]))
                colorSet.add(colorsTab[y][x][z + 1]);
            if ((y + 1 < height) && (z - 1 >= 0) && isEdge(x, y, z - 1, myColor) && myColor.equals(colorsTab[y][x][z - 1]))
                colorSet.add(colorsTab[y][x][z - 1]);
            if ((y + 1 < height) && (z + 1 < Z) && isEdge(x, y + 1, z + 1, myColor) && myColor.equals(colorsTab[y + 1][x][z + 1]))
                colorSet.add(colorsTab[y + 1][x][z + 1]);
            if ((y + 1 < height) && (z - 1 >= 0) && isEdge(x, y + 1, z - 1, myColor) && myColor.equals(colorsTab[y + 1][x][z - 1]))
                colorSet.add(colorsTab[y + 1][x][z - 1]);
            if ((y + 1 < height) && isEdge(x, y + 1, z, myColor) && myColor.equals(colorsTab[y + 1][x][z]))
                colorSet.add(colorsTab[y + 1][x][z]);
            if ((y + 1 < height) && (x + 1 < width) && isEdge(x + 1, y + 1, z, myColor) && myColor.equals(colorsTab[y + 1][x + 1][z]))
                colorSet.add(colorsTab[y + 1][x + 1][z]);
            if ((x + 1 < width) && isEdge(x + 1, y, z, myColor) && myColor.equals(colorsTab[y][x + 1][z]))
                colorSet.add(colorsTab[y][x + 1][z]);
            if ((x + 1 < width) && (y - 1 >= 0) && isEdge(x + 1, y - 1, z, myColor) && myColor.equals(colorsTab[y - 1][x + 1]))
                colorSet.add(colorsTab[y][x + 1][z]);
            if ((y - 1 >= 0) && (z + 1 < Z) && isEdge(x, y - 1, z + 1, myColor) && myColor.equals(colorsTab[y - 1][x][z + 1]))
                colorSet.add(colorsTab[y - 1][x][z + 1]);
            if ((y - 1 >= 0) && (z - 1 >= 0) && isEdge(x, y - 1, z - 1, myColor) && myColor.equals(colorsTab[y - 1][x][z - 1]))
                colorSet.add(colorsTab[y - 1][x][z - 1]);
            if ((y - 1 >= 0) && isEdge(x, y - 1, z, myColor) && myColor.equals(colorsTab[y - 1][x][z]))
                colorSet.add(colorsTab[y - 1][x][z]);
            if ((y - 1 >= 0) && (x - 1 >= 0) && isEdge(x - 1, y - 1, z, myColor) && myColor.equals(colorsTab[y - 1][x - 1][z]))
                colorSet.add(colorsTab[y - 1][x - 1][z]);
            if ((x - 1 >= 0) && isEdge(x - 1, y, z, myColor) && myColor.equals(colorsTab[y][x - 1][z]))
                colorSet.add(colorsTab[y][x - 1][z]);
            if ((x - 1 >= 0) && (y + 1 < height) && isEdge(x - 1, y + 1, z, myColor) && myColor.equals(colorsTab[y + 1][x - 1][z]))
                colorSet.add(colorsTab[y + 1][x - 1][z]);
        }
        return new Color[0];
    }

    @Override
    public int getEnergy(int x, int y, int z, Color color) {
        int energy = 0;
        Color myRealColor = colorsTab[y][x][z];
        colorsTab[y][x][z] = color;
        if (isPeriodic) {
            if (isEdge(x, y, (z + 1) % Z))
                energy++;
            if (isEdge(x, y, (z - 1 + Z) % Z))
                energy++;
            if (isEdge(x, (y + 1) % height, z))
                energy++;
            if (isEdge(x, (y + 1) % height, (z + 1) % Z))
                energy++;
            if (isEdge(x, (y + 1) % height, (z - 1 + Z) % Z))
                energy++;
            if (isEdge((x + 1) % width, (y + 1) % height, z))
                energy++;
            if (isEdge((x + 1) % width, y, z))
                energy++;
            if (isEdge((x + 1) % width, (y - 1 + height) % height, z))
                energy++;
            if (isEdge(x, (y - 1 + height) % height, z))
                energy++;
            if (isEdge(x, (y - 1 + height) % height, (z + 1) % Z))
                energy++;
            if (isEdge(x, (y - 1 + height) % height, (z - 1 + Z) % Z))
                energy++;
            if (isEdge((x - 1 + width) % width, (y - 1 + height) % height, z))
                energy++;
            if (isEdge((x - 1 + width) % width, y, z))
                energy++;
            if (isEdge((x - 1 + width) % width, (y + 1) % height,z))
                energy++;
        } else {
            if ((z + 1 < Z) && isEdge(x, y ,z+1))
                energy++;
            if ((z - 1 >= 0) && isEdge(x, y ,z-1))
            energy++;
            if ((y + 1 < height) && isEdge(x, y + 1,z))
                energy++;
            if ((y + 1 < height)&&(z + 1 < Z) && isEdge(x, y + 1,z+1))
            energy++;
            if ((y + 1 < height)&&(z - 1 >= 0) && isEdge(x, y + 1,z-1))
                energy++;
            if ((y + 1 < height) && (x + 1 < width) && isEdge(x + 1, y + 1,z))
                energy++;
            if ((x + 1 < width) && isEdge(x + 1, y,z))
                energy++;
            if ((x + 1 < width) && (y - 1 >= 0) && isEdge(x + 1, y - 1,z))
                energy++;
            if ((y - 1 >= 0) && isEdge(x, y - 1,z))
                energy++;
            if ((y - 1 >= 0)&&(z + 1 < Z) && isEdge(x, y - 1,z+1))
            energy++;
            if ((y - 1 >= 0)&&(z - 1 >= 0) && isEdge(x, y - 1,z-1))
                energy++;
            if ((y - 1 >= 0) && (x - 1 >= 0) && isEdge(x - 1, y - 1,z))
                energy++;
            if ((x - 1 >= 0) && isEdge(x - 1, y,z))
                energy++;
            if ((x - 1 >= 0) && (y + 1 < height) && isEdge(x - 1, y + 1,z))
                energy++;
        }
        colorsTab[y][x][z] = myRealColor;
        return energy;
    }

    @Override
    public boolean isEdge(int x, int y,int z, Color color) {
        Color thisColor = color;
        if (isPeriodic) {
            if (!thisColor.equals(colorsTab[y][x][(z + 1) % Z]))
                return true;
            if (!thisColor.equals(colorsTab[y][x][(z - 1 + Z) % Z]))
            return true;
            if (!thisColor.equals(colorsTab[(y + 1) % height][x][z]))
                return true;
            if (!thisColor.equals(colorsTab[(y + 1) % height][x][(z + 1) % Z]))
            return true;
            if (!thisColor.equals(colorsTab[(y + 1) % height][x][(z - 1 + Z) % Z]))
                return true;
            if (!thisColor.equals(colorsTab[(y + 1) % height][(x + 1) % width][z]))
                return true;
            if (!thisColor.equals(colorsTab[y][(x + 1) % width][z]))
                return true;
            if (!thisColor.equals(colorsTab[(y - 1 + height) % height][(x + 1) % width][z]))
                return true;
            if (!thisColor.equals(colorsTab[(y - 1 + height) % height][x][z]))
                return true;
            if (!thisColor.equals(colorsTab[(y - 1 + height) % height][x][(z + 1) % Z]))
            return true;
            if (!thisColor.equals(colorsTab[(y - 1 + height) % height][x][(z - 1 + Z) % Z]))
                return true;
            if (!thisColor.equals(colorsTab[(y - 1 + height) % height][(x - 1 + width) % width][z]))
                return true;
            if (!thisColor.equals(colorsTab[y][(x - 1 + width) % width][z]))
                return true;
            return !thisColor.equals(colorsTab[(y + 1) % height][(x - 1 + width) % width][z]);
        } else {
            if ((z + 1 < Z) && !thisColor.equals(colorsTab[y ][x][z+1]))
                return true;
            if ((z - 1 >= 0)&& !thisColor.equals(colorsTab[y][x][z-1]))
            return true;
            if ((y + 1 < height) && !thisColor.equals(colorsTab[y + 1][x][z]))
                return true;
            if ((y + 1 < height)&&(z + 1 < Z) && !thisColor.equals(colorsTab[y + 1][x][z+1]))
            return true;
            if ((y + 1 < height)&&(z - 1 >= 0) && !thisColor.equals(colorsTab[y + 1][x][z-1]))
                return true;
            if ((y + 1 < height && x + 1 < width) && !thisColor.equals(colorsTab[y + 1][x + 1][z]))
                return true;
            if ((x + 1 < width) && !thisColor.equals(colorsTab[y][x + 1][z]))
                return true;
            if ((y - 1 >= 0 && x + 1 < width) && !thisColor.equals(colorsTab[y - 1][x + 1][z]))
                return true;
            if ((y - 1 >= 0) && !thisColor.equals(colorsTab[y - 1][x][z]))
                return true;
            if ((y - 1 >= 0)&&(z + 1 < Z) && !thisColor.equals(colorsTab[y - 1][x][z+1]))
            return true;
            if ((y - 1 >= 0) &&(z - 1 >= 0)&& !thisColor.equals(colorsTab[y - 1][x][z-1]))
                return true;
            if ((y - 1 >= 0 && x - 1 >= 0) && !thisColor.equals(colorsTab[y - 1][x - 1][z]))
                return true;
            if ((x - 1 >= 0) && !thisColor.equals(colorsTab[y][x - 1][z]))
                return true;
            return (y + 1 < height && x - 1 >= 0) && !thisColor.equals(colorsTab[y + 1][x - 1][z]);
        }
    }

    @Override
    public Integer[][][] getNeighbours(int x, int y,int z) {
        List<Integer[]> neighbours = new ArrayList<>();
        if (isPeriodic) {
            neighbours.add(new Integer[]{x, y,(z + 1) % Z});
            neighbours.add(new Integer[]{x,y,(z - 1 + Z) % Z});
            neighbours.add(new Integer[]{x, (y + 1) % height,z});
            neighbours.add(new Integer[]{x, (y + 1) % height,(z + 1) % Z});
            neighbours.add(new Integer[]{x, (y + 1) % height,(z - 1 + Z) % Z});
            neighbours.add(new Integer[]{(x + 1) % width, (y + 1) % height,z});
            neighbours.add(new Integer[]{(x + 1) % width, y,z});
            neighbours.add(new Integer[]{(x + 1) % width, (y - 1 + height) % height,z});
            neighbours.add(new Integer[]{x, (y - 1 + height) % height,z});
            neighbours.add(new Integer[]{x, (y - 1 + height) % height,(z + 1) % Z});
            neighbours.add(new Integer[]{x, (y - 1 + height) % height,(z - 1 + Z) % Z});
            neighbours.add(new Integer[]{(x - 1 + width) % width, (y - 1 + height) % height,z});
            neighbours.add(new Integer[]{(x - 1 + width) % width, y,z});
            neighbours.add(new Integer[]{(x - 1 + width) % width, (y + 1) % height,z});
        } else {
            if ((z + 1 < Z))
                neighbours.add(new Integer[]{x, y + 1,z+1});
            if (z - 1 >= 0)
            neighbours.add(new Integer[]{x, y + 1,z-1});
            if ((y + 1 < height)&&(z + 1 < Z))
                neighbours.add(new Integer[]{x, y + 1,z+1});
            if ((y + 1 < height)&&(z - 1 >= 0))
            neighbours.add(new Integer[]{x, y + 1,z-1});
            if ((y + 1 < height))
                neighbours.add(new Integer[]{x, y + 1,z});
            if ((y + 1 < height && x + 1 < width))
                neighbours.add(new Integer[]{x + 1, y + 1,z});
            if ((x + 1 < width))
                neighbours.add(new Integer[]{x + 1, y,z});
            if ((y - 1 >= 0 && x + 1 < width))
                neighbours.add(new Integer[]{x + 1, y - 1,z});
            if ((y - 1 >= 0))
                neighbours.add(new Integer[]{x, y - 1,z});
            if ((y - 1 >= 0)&&(z + 1 < Z))
            neighbours.add(new Integer[]{x, y - 1,z+1});
            if ((y - 1 >= 0)&&(z - 1 >= 0))
                neighbours.add(new Integer[]{x, y - 1,z-1});
            if ((y - 1 >= 0) && x - 1 >= 0)
                neighbours.add(new Integer[]{x - 1, y - 1,z});
            if ((x - 1 >= 0))
                neighbours.add(new Integer[]{x - 1, y,z});
            if ((y + 1 < height && x - 1 >= 0))
                neighbours.add(new Integer[]{x - 1, y + 1,z});
        }
        return neighbours.toArray(new Integer[neighbours.size()][][]);
    }
}
