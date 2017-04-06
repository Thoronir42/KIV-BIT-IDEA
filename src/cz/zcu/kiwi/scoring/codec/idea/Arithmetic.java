package cz.zcu.kiwi.scoring.codec.idea;

public class Arithmetic {

    private final int mask;
    private final int modulo;

    public Arithmetic(int maskLength, int moduloPower) {
        this.mask = this.createMask(maskLength);
        this.modulo = 2 ^ moduloPower;
    }

    private int createMask(int length) {
        if(length < 0) {
            return 0;
        }
        int mask = 1;
        while (length-- > 0) {
            mask = (mask << 1) | 1;
        }

        return mask;
    }

    int xor(int a, int b) {
        return (a ^ b) & this.mask;
    }

    int add(int a, int b) {
        return (a + b) % this.modulo;
    }

    int mult(int a, int b) {
        return (a * b) % this.modulo + 1;
    }


}
