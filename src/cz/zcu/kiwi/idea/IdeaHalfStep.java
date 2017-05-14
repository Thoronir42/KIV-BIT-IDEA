package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.idea.data.Chunk;
import static cz.zcu.kiwi.cryptography.Arithmetic.*;

class IdeaHalfStep extends AIdeaStep {

    IdeaHalfStep(Chunk input, boolean encrypt) {
        super(input, encrypt);
    }

    @Override
    Chunk execute(IdeaKey key, int round) {
        return new Chunk(
                mult(input.getBlock(0), key.subKey(0, round, this.encrypt)),
                add (input.getBlock(1), key.subKey(1, round, this.encrypt)),
                add (input.getBlock(2), key.subKey(2, round, this.encrypt)),
                mult(input.getBlock(3), key.subKey(3, round, this.encrypt))
        );
    }
}
