package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;
import cz.zcu.kiwi.idea.data.Chunk;

public class IdeaHalfStep extends AIdeaStep {

    IdeaHalfStep(Chunk input, Arithmetic a) {
        super(input, a);
    }

    @Override
    Chunk execute(IdeaKey key, int round) {
        return new Chunk(
                a.mult(input.getBlock(0), key.subKey(0, round)),
                a.add (input.getBlock(1), key.subKey(1, round)),
                a.add (input.getBlock(2), key.subKey(2, round)),
                a.mult(input.getBlock(3), key.subKey(3, round))
        );
    }
}
