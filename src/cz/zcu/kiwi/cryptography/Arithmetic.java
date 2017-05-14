package cz.zcu.kiwi.cryptography;

public class Arithmetic {

    private static final int IDEA_MASK = 0xFFFF; // 2 ^ 16

    private Arithmetic() {
    }

    public static int xor(int a, int b) {
        return (a ^ b) & IDEA_MASK;
    }

    public static int add(int a, int b) {
        return (a + b) % IDEA_MASK;
    }

    public static int mult(int x, int y) {
        long m = (long) x * y;
        if (m != 0) {
            return (int) (m % 0x10001) & IDEA_MASK;
        } else {
            if (x != 0 || y != 0) {
                return (1 - x - y) & IDEA_MASK;
            }
            return 0;
        }
    }

    public static int inv(int a) {
        return (IDEA_MASK - a + 1) & IDEA_MASK;
    }

    public static int modularInverse(int a) {
        return modularInverse(a, 0x10001);
    }

    public static int modularInverse(int a, int b) {
        int q;
        int r;
        int t;
        int u = 0;
        int v = 1;


        while (a > 0) {
            q = b / a;
            r = b % a;

            b = a;
            a = r;

            t = v;
            v = u - q * v;
            u = t;
        }

        if (u < 0)
            u += b;

        return u & IDEA_MASK;
    }

    public static int concatBytes(byte a, byte b) {
        int ia = a & 0xFF;
        int ib = b & 0xFF;

        return (ia << 8) | ib;
    }

}
