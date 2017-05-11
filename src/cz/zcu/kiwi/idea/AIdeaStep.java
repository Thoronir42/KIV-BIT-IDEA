package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;

abstract class AIdeaStep {

    final int[] input;
    final Arithmetic a;
    int[] output;

    AIdeaStep(int[] input, Arithmetic a) {
        this.input = input;
        this.a = a;
    }

    abstract int[] execute(IdeaKey key, int round);

    int[] getOutput() {
        return output;
    }
}
