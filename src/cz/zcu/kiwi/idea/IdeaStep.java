package cz.zcu.kiwi.idea;

import static cz.zcu.kiwi.cryptography.Arithmetic.*;

class IdeaStep extends AIdeaStep {

    IdeaStep(Chunk input, boolean encrypt) {
        super(input, encrypt);
    }

    Chunk execute(IdeaKey key, int round) {
        int ak1 = mult(input.getBlock(0), key.subKey(0, round, this.encrypt));
        int ak2 = add (input.getBlock(1), key.subKey(1, round, this.encrypt));
        int ak3 = add (input.getBlock(2), key.subKey(2, round, this.encrypt));
        int ak4 = mult(input.getBlock(3), key.subKey(3, round, this.encrypt));

        int b13 = xor(ak1, ak3);
        int b24 = xor(ak2, ak4);

        int bk135 = mult(b13, key.subKey(4, round, this.encrypt));
        int c1 = add(b24, bk135);

        int ck16 = mult(c1, (key.subKey(5, round, this.encrypt)));
        int d1 = mult(bk135, ck16);

        return new Chunk(xor(ak1, ck16), xor(ak3, ck16), xor(ak2, d1), xor(ak4, d1));
    }

    IdeaStep nextStep(IdeaKey key, int round) {
        return new IdeaStep(this.execute(key, round), encrypt);
    }

    IdeaHalfStep nextHalfStep(IdeaKey key, int round) {
        return new IdeaHalfStep(this.execute(key, round), encrypt);
    }
}
