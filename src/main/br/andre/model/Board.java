package main.br.andre.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private int rows;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        associateNeighbors();
        sortMines();
    }

    public void open(int row, int column) {
        fields.stream()
                .filter(f -> f.getRow() == row && f.getColumn() == column)
                .findFirst()
                .ifPresent(Field::open);
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
        long fieldsMined = 0;
        Predicate<Field> mined = Field::isMined;

        do {
            fieldsMined = fields.stream().filter(mined).count();
            int random = (int)(Math.random() * fields.size());
            fields.get(random).setMined();
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

        int i = 0;
        for (int r = 0; r < rows; r++) {
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
