package cz.zcu.kiwi.cryptography;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArithmeticTest {

    @Test
    public void add() throws Exception {
        int[][] data = new int[][]{
                new int[]{0x0, 0x0, 0x0},
                new int[]{0x1, 0x10, 0x11},
                new int[]{0xFFFF, 0x0, 0xFFFF},
                new int[]{0x0, 0xFFFF, 0xFFFF},
                new int[]{0xFFFF, 0x0, 0xFFFF},
                new int[]{0xFFFF, 0xFFFF, 0xFFFE},
        };

        for(int[] row : data) {
            int actual = Arithmetic.add(row[0], row[1]);
            int expected = row[2];

            String message = String.format("%X + %X should be %X but was %X", row[0], row[1], row[2], actual);
            assertEquals(message, expected, actual);
        }
    }

    @Test
    public void mult() throws Exception {
        int[][] data = new int[][]{
//                new int[]{0x0, 0x0, 0x0},
                new int[]{0x1, 0x10, 0x10},
                new int[]{0x20, 0x20, 0x400},
        };

        for(int[] row : data) {
            int actual = Arithmetic.mult(row[0], row[1]);
            int expected = row[2];

            String message = String.format("%X * %X should be %X but was %X", row[0], row[1], row[2], actual);
            assertEquals(message, expected, actual);
        }
    }

    @Test
    public void modularInverse() throws Exception {
        int[][] data = new int[][]{
                new int[]{0x0, 0x0},
                new int[]{0x1, 0x1},

                new int[]{0x64, 0x451F},
                new int[]{0x3E8, 0x86EA},
//                new int[]{0x10000, 0x0},

                new int[]{0x10001, 0x0},
                new int[]{0x10002, 0x1},
        };

        for (int[] row : data) {
            int actual = Arithmetic.modularInverse(row[0]);

            String message = String.format("modInv(%04X, FFFF) should be %04X but was %04X", row[0], row[1], actual);
            assertEquals(message, row[1], actual);
        }
    }

    @Test
    public void concatBytes() {
        int[][] data = new int[][]{
                new int[]{0x000A, 0x00, 0x0A},
                new int[]{0x0A00, 0x0A, 0x00},
                new int[]{0x0A0B, 0x0A, 0x0B},
                new int[]{0x9ABC, 0x9A, 0xBC},
                new int[]{0xFFFF, 0xFF, 0xFF},
        };

        for (int[] row : data) {
            byte a = (byte) row[1], b = (byte) row[2];
            int expected = row[0];
            int actual = Arithmetic.concatBytes(a, b);

            String message = String.format("concat(%02X . %02X) is %04X, should be %04X", a, b, actual, expected);
            assertEquals(message, expected, actual);
        }
    }

}