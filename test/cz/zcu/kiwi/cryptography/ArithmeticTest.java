package cz.zcu.kiwi.cryptography;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArithmeticTest {

    public void modularInverse() throws Exception {
        int[][] data = new int[][]{
                new int[]{1, 1, 3},
                new int[]{2, 2, 3},

                new int[]{3, 2, 5},
                new int[]{2, 3, 5},
                new int[]{4, 4, 5},

                new int[]{1, 1, 7},
                new int[]{4, 2, 7},
                new int[]{5, 3, 7},
                new int[]{6, 6, 7},
        };

        for (int[] row : data) {
            String message = String.format("mod(%d, %d)", row[1], row[2]);
            assertEquals(message, row[0], Arithmetic.modularInverse(row[1], row[2]));
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