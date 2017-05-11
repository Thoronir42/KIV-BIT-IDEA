package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;

class IdeaStep extends AIdeaStep {

    IdeaStep(int[] input, Arithmetic a) {
        super(input, a);
    }

    int[] execute(IdeaKey key, int round) {
        int ak1 = a.mult(input[0], key.subKey(0, round));
        int ak2 = a.add(input[1], key.subKey(1, round));
        int ak3 = a.add(input[2], key.subKey(2, round));
        int ak4 = a.mult(input[3], key.subKey(3, round));

        int b13 = a.xor(ak1, ak3);
        int b24 = a.xor(ak2, ak4);

        int bk135 = a.mult(b13, key.subKey(4, round));
        int c1 = a.add(b24, bk135);

        int ck16 = a.mult(c1, (key.subKey(5, round)));
        int d1 = a.mult(bk135, ck16);

        return this.output = new int[]{
                a.xor(ak1, ck16),
                a.xor(ak3, ck16),
                a.xor(ak2, d1),
                a.xor(ak4, d1)
        };
    }

    IdeaStep nextStep(IdeaKey key, int round) {
        return new IdeaStep(this.execute(key, round), a);
    }

    IdeaHalfStep nextHalfStep(IdeaKey key, int round) {
        return new IdeaHalfStep(this.execute(key, round), a);
    }
}
