package cz.zcu.kiwi.scoring.codec.idea;

abstract class AIdeaStep {

    final int[] input;
    final Arithmetic a;
    int[] output;

    AIdeaStep(int[] input, Arithmetic a) {
        this.input = input;
        this.a = a;
    }

    abstract int[] execute(int[] keys);

    int[] getOutput() {
        return output;
    }
}
