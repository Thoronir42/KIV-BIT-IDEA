package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;
import cz.zcu.kiwi.idea.data.Chunk;

abstract class AIdeaStep {

    final Chunk input;
    final Arithmetic a;
    final boolean encrypt;

    Chunk output;

    AIdeaStep(Chunk input, boolean encrypt) {
        this.input = input;
        this.encrypt = encrypt;
        this.a = new Arithmetic();
    }

    abstract Chunk execute(IdeaKey key, int round);
}
