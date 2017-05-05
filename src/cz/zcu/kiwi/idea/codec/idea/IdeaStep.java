package cz.zcu.kiwi.idea.codec.idea;

class IdeaStep extends AIdeaStep{

    IdeaStep(int[] input, Arithmetic a) {
        super(input, a);
    }

    int[] execute(int[] keys) {
        int ak1 = a.mult(input[0], keys[0]);
        int ak2 = a.add(input[1], keys[1]);
        int ak3 = a.add(input[2], keys[2]);
        int ak4 = a.mult(input[3], keys[3]);

        int b13 = a.xor(ak1, ak3);
        int b24 = a.xor(ak2, ak4);

        int bk135 = a.mult(b13, keys[4]);
        int c1 = a.add(b24, bk135);

        int ck16 = a.mult(c1, keys[5]);
        int d1 = a.mult(bk135, ck16);

        return this.output = new int[]{
                a.xor(ak1, ck16),
                a.xor(ak3, ck16),
                a.xor(ak2, d1),
                a.xor(ak4, d1)
        };
    }
}
