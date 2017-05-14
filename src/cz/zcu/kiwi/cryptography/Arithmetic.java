package cz.zcu.kiwi.cryptography;

public final class Arithmetic {

    private static final int IDEA_MASK = 0xFFFF; // 2^16 - 1
    private static final int IDEA_2_16 = 0x10000; // 2^16

    Arithmetic() {
    }

    public static int xor(int a, int b) {
        return (a ^ b) & IDEA_MASK;
    }

    public static int add(int a, int b) {
        return (a + b) & IDEA_MASK;
    }

    public static int addInv(int a) {
        return (IDEA_2_16 - a) & IDEA_MASK;
    }

    public static int mult(int x, int y) {
        if (x == 0) {
            x = IDEA_2_16;
        }
        if (y == 0) {
            y = IDEA_2_16;
        }

        int result = (int) (((long) x * y) % 0x10001);

        return result != IDEA_2_16 ? result : 0;
    }

    public static int modularInverse(int a) {
        if (a <= 1) {
            return a;
        }
        int y = 0x10001;
        int t0 = 1;
        int t1 = 0;
        while (true) {
            t1 += y / a * t0;
            y %= a;
            if (y == 1) {
                return 0x10001 - t1;
            } else if (y == 0) {
                return 0;
            }
            t0 += a / y * t1;
            a %= y;
            if (a == 1) {
                return t0;
            }
        }
    }

    public static int concatBytes(byte a, byte b) {
        int ia = a & 0xFF;
        int ib = b & 0xFF;

        return (ia << 8) | ib;
    }

}
