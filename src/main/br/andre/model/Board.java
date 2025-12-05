package main.br.andre.model;

import main.br.andre.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private final int rows;
    private final int columns;
    private final int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        associateNeighbors();
        sortMines();
    }

    public List<Field> getFields() {
        return fields;
    }

    public Field getField(int row, int col) {
        return fields.stream()
                .filter(f -> f.getRow() == row && f.getColumn() == col)
                .findFirst()
                .orElseThrow();
    }

    public void open(int row, int column) {
        try {
            fields.stream()
                    .filter(f -> f.getRow() == row && f.getColumn() == column)
                    .findFirst()
                    .ifPresent(Field::open);
        } catch (ExplosionException e) {
            fields.forEach(f -> f.setOpened(true));
            throw e;
        }
    }

    public void mark(int row, int column) {
        fields.stream()
                .filter(f -> f.getRow() == row && f.getColumn() == column)
                .findFirst()
                .ifPresent(Field::alterMarked);
    }

    private void generateFields() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                fields.add(new Field(r, c));
            }
        }
    }

    private void associateNeighbors() {
        for (Field f1 : fields) {
            for (Field f2 : fields) {
                f1.addNeighbor(f2);
            }
        }
    }

    private void sortMines() {
        if (mines <= 0) return;

        long fieldsMined;
        Predicate<Field> mined = Field::isMined;

        do {
            int random = (int)(Math.random() * fields.size());
            fields.get(random).setMined();
            fieldsMined = fields.stream().filter(mined).count();
        } while (fieldsMined < mines);
    }

    public boolean targetReached() {
        return fields.stream().allMatch(Field::targetReached);
    }

    public void reset() {
        fields.forEach(Field::reset);
        sortMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("     ");
        for (int f = 0; f < columns; f++) {
            sb.append(f).append("  ");
        }
        sb.append("\n");sb.append("     ");
        for (int f = 0; f < columns; f++) {
            sb.append("|").append("  ");
        }
        sb.append("\n");

        int i = 0;
        for (int r = 0; r < rows; r++) {
            sb.append(r).append(" ——");
            for (int c = 0; c < columns; c++) {
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
