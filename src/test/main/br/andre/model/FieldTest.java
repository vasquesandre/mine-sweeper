package main.br.andre.model;

import main.br.andre.exception.ExplosionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private Field field;

    @BeforeEach
    void initField() {
        field = new Field(3,3);
    }

    @Test
    void testRealNeighborEquals1() {
        Field neighbor = new Field(3,2);
        boolean result = field.addNeighbor(neighbor);

        assertTrue(result);
    }

    @Test
    void testRealNeighborEquals2() {
        Field neighbor = new Field(2,2);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void testNotRealNeighbor() {
        Field neighbor = new Field(4,5);
        boolean result = field.addNeighbor(neighbor);
        assertFalse(result);
    }

    @Test
    void testDefaultIsMarkedValue() {
        assertFalse(field.isMarked());
    }

    @Test
    void testAlterMarked() {
        field.alterMarked();
        assertTrue(field.isMarked());
    }

    @Test
    void testDefaultIsMinedValue() {
        assertFalse(field.isMined());
    }

    @Test
    void testAlterMarkedTwoTimes() {
        field.alterMarked();
        field.alterMarked();
        assertFalse(field.isMarked());
    }

    @Test
    void testOpenFieldNotMarkedNotMined() {
        assertTrue(field.open());
    }

    @Test
    void testOpenFieldMarkedNotMined() {
        field.alterMarked();
        assertFalse(field.open());
    }

    @Test
    void testOpenFieldMarkedMined() {
        field.alterMarked();
        field.setMined();
        assertFalse(field.open());
    }

    @Test
    void testOpenFieldNotMarkedMined() {
        field.setMined();

        assertThrows(ExplosionException.class, () -> field.open());
    }

    @Test
    void testOpenSafeNeighbor() {
        Field n1 = new Field(1,1);
        Field n2 = new Field(2,2);

        n2.addNeighbor(n1);
        field.addNeighbor(n2);
        field.open();

        assertTrue(n1.isOpened() && n2.isOpened());
    }

    @Test
    void testOpenMinedNeighbor() {
        Field n1 = new Field(1,1);
        Field n2 = new Field(1,2);
        n2.setMined();

        Field n3 = new Field(2,2);

        n3.addNeighbor(n1);
        n3.addNeighbor(n2);
        field.addNeighbor(n3);
        field.open();

        assertTrue(n3.isOpened() && !n1.isOpened());
    }

    @Test
    void testReset() {
        field.setMined();
        field.reset();
        assertFalse(field.isMined());
        assertFalse(field.isMarked());
        assertFalse(field.isOpened());
    }

    @Test
    void testTargetReachedFirstCondition() {
        field.open();
        assertTrue(field.targetReached());
    }

    @Test
    void testTargetReachedSecondCondition() {
        field.setMined();
        field.alterMarked();
        assertTrue(field.targetReached());
    }

    @Test
    void testGetRow() {
        assertEquals(3, field.getRow());
    }

    @Test
    void testGetColumn() {
        assertEquals(3, field.getColumn());
    }
}