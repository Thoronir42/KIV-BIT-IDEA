package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;
import cz.zcu.kiwi.idea.data.Chunk;

abstract class AIdeaStep {

    final Chunk input;
    final Arithmetic a;
    Chunk output;

    AIdeaStep(Chunk input, Arithmetic a) {
        this.input = input;
        this.a = a;
    }

    abstract Chunk execute(IdeaKey key, int round);
}
