package edu.nps.moves.deadreckoning.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixTest {

    @Test
    public void testMultMatrixMatrix() throws MatrixException {
        /**
         * A simple example to illustrate the error in old Matrix mult method
         * 
         * 1 0 0   1           1         1
         * 0 1 0 * 1 should be 1 but was 0
         * 0 0 1   1           1         0
         */
        Matrix eye3 = new Matrix(3);
        Matrix onesvec3 = new Matrix(3, 1);
        onesvec3.setCell(0, 0, 1.0);
        onesvec3.setCell(1, 0, 1.0);
        onesvec3.setCell(2, 0, 1.0);
        Matrix product = eye3.mult(onesvec3);
        assertEquals(1.0, product.cell(0, 0), 1.0e-6);
        assertEquals(1.0, product.cell(1, 0), 1.0e-6);
        assertEquals(1.0, product.cell(2, 0), 1.0e-6);
    }

}
