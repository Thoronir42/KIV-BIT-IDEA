package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;
import cz.zcu.kiwi.cryptography.Key;

public class IdeaHalfStep extends AIdeaStep {

    IdeaHalfStep(int[] input, Arithmetic a) {
        super(input, a);
    }

    @Override
    int[] execute(IdeaKey key, int round) {
        return output = new int[]{
                a.mult(input[0], key.subKey(0, round)),
                a.add(input[1], key .subKey(1, round)),
                a.add(input[2], key .subKey(2, round)),
                a.mult(input[3], key.subKey(3, round))
        };
    }
}
