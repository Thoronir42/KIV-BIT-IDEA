package cz.zcu.kiwi.scoring.codec.idea;

class IdeaStep {
    private final int[] input;
    private final Arithmetic a;
    private final int[] output;

    public IdeaStep(int[] input, Arithmetic a) {
        this.input = input;
        this.output = new int[input.length];
        this.a = a;
    }

    public int[] execute(int[] keys) {
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

        this.output[0] = a.xor(ak1, ck16);
        this.output[1] = a.xor(ak3, ck16);
        this.output[2] = a.xor(ak2, d1);
        this.output[3] = a.xor(ak4, d1);

        return this.getOutput();
    }



    public int[] getOutput() {
        return output;
    }
}
