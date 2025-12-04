package main.br.andre.model;

import main.br.andre.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int row;
    private final int column;

    private boolean mined;
    private boolean opened;
    private boolean marked;

    private List<Field> neighbors = new ArrayList<>();

    Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isMined() {
        return mined;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isMarked() {
        return marked;
    }

    void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    void setMined() {
        mined = true;
    }

    boolean targetReached() {
        boolean revealedAndSafe = !mined && opened;
        boolean hiddenAndMarked = mined && marked;

        return revealedAndSafe || hiddenAndMarked;
    }

    long minesInTheNeighborhood() {
        return neighbors.stream().filter(Field::isMined).count();
    }

    void reset() {
        opened = false;
        marked = false;
        mined = false;
    }

    boolean addNeighbor(Field neighbor) {
        boolean neighborRow = row != neighbor.row;
        boolean neighborColumn = column != neighbor.column;
        boolean diagonal = neighborRow && neighborColumn;

        int deltaRow = Math.abs(row - neighbor.row);
        int deltaColumn = Math.abs(column - neighbor.column);
        int delta = deltaRow + deltaColumn;

        if(delta == 1) {
            neighbors.add(neighbor);
            return true;
        } else if(delta == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }

    void alterMarked() {
        if(!opened) {
            marked = !marked;
        }
    }

    boolean open() {
        if(!opened && !marked) {
            opened = true;

            if(mined) {
                throw new ExplosionException();
            }

            if(safeNeighbor()) {
                neighbors.forEach(Field::open);
            }

            return true;
        } else {
            return false;
        }
    }

    boolean safeNeighbor() {
        return neighbors.stream()
                .noneMatch(n -> n.mined);
    }

    @Override
    public String toString() {
        if(marked) {
            return "x";
        } else if(opened && mined) {
            return "*";
        } else if(opened && minesInTheNeighborhood() > 0) {
            return Long.toString(minesInTheNeighborhood());
        } else if(opened) {
            return " ";
        } else {
            return "?";
        }
    }
}
