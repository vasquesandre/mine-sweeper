package main.br.andre.model;

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
}