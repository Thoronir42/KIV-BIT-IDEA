package cz.zcu.kiwi.idea;

import static cz.zcu.kiwi.cryptography.Arithmetic.*;

class IdeaStep extends AIdeaStep {

    IdeaStep(Chunk input, boolean encrypt) {
        super(input, encrypt);
    }

    Chunk execute(IdeaKey key, int round) {
        int a = mult(input.getBlock(0), key.subKey(0, round, this.encrypt));
        int b = add (input.getBlock(1), key.subKey(1, round, this.encrypt));
        int c = add (input.getBlock(2), key.subKey(2, round, this.encrypt));
        int d = mult(input.getBlock(3), key.subKey(3, round, this.encrypt));

        int e = mult(xor(a, c), key.subKey(4, round, this.encrypt));
        int f = add(xor(b, d), e);
        int g = mult(f, (key.subKey(5, round, this.encrypt)));
        int h = add(e, g);

        return new Chunk(xor(a, g), xor(c, g), xor(b, h), xor(d, h));
    }

    IdeaStep nextStep(IdeaKey key, int round) {
        return new IdeaStep(this.execute(key, round), encrypt);
    }

    IdeaHalfStep nextHalfStep(IdeaKey key, int round) {
        return new IdeaHalfStep(this.execute(key, round), encrypt);
    }
}
