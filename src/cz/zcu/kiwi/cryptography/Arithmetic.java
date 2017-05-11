package cz.zcu.kiwi.cryptography;

public class Arithmetic {

    private final int mask;

    public Arithmetic() {
        this.mask = 0xFFFF; // 2 ^ 16
    }

    public int xor(int a, int b) {
        return (a ^ b) & this.mask;
    }

    public int add(int a, int b) {
        return (a + b) % this.mask;
    }

    public int mult(int a, int b) {
        return (a * b) % (this.mask + 1);
    }


}
