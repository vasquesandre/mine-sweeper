package main.br.andre.model;

import main.br.andre.exception.ExplosionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    private Board safeBoard;

    @BeforeEach
    void setUp() {
        board = new Board(6,6,6);
        safeBoard = new Board(6,6,0);
    }

    @Test
    void testDefaultValues() {
        List<Field> fields = safeBoard.getFields();
        boolean notOpened = fields.stream().noneMatch(Field::isOpened);
        boolean notMarked = fields.stream().noneMatch(Field::isMarked);
        boolean notMined = fields.stream().noneMatch(Field::isMined);
        assertTrue(notOpened);
        assertTrue(notMarked);
        assertTrue(notMined);
    }

    @Test
    void testOpenSafeField() {
        Board safeBoard = new Board(3, 3, 0); // sem minas
        safeBoard.open(1, 1);

        Field field = safeBoard.getField(1, 1);
        assertTrue(field.isOpened());
    }

    @Test
    void testOpenMinedFieldThrowsException() {
        Field f = safeBoard.getField(1,1);
        f.setMined();
        assertThrows(ExplosionException.class, () -> safeBoard.open(1,1));
    }

    @Test
    void testMarkField() {
        Field f = board.getField(1,1);
        board.mark(1,1);
        assertTrue(f.isMarked());
    }

    @Test
    void testTargetReached() {
        List<Field> fields = board.getFields();
        fields.stream().filter(f -> !f.isMined()).forEach(Field::open);
        fields.stream().filter(Field::isMined).forEach(Field::alterMarked);
        assertTrue(board.targetReached());
    }
}