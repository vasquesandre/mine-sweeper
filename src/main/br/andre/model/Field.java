package main.br.andre.model;

import main.br.andre.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int row;
    private final int column;

    private boolean isMined;
    private boolean isOpened;
    private boolean isMarked;

    private List<Field> neighbors = new ArrayList<>();

    Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isMined() {
        return isMined;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean isMarked() {
        return isMarked;
    }

    void setMined() {
        isMined = true;
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
        if(!isOpened) {
            isMarked = !isMarked;
        }
    }

    boolean open() {
        if(!isOpened && !isMarked) {
            isOpened = true;

            if(isMined) {
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
                .noneMatch(n -> n.isMined);
    }
}
