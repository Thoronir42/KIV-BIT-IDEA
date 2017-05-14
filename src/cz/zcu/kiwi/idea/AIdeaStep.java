package cz.zcu.kiwi.idea;

abstract class AIdeaStep {

    final Chunk input;
    final boolean encrypt;

    Chunk output;

    AIdeaStep(Chunk input, boolean encrypt) {
        this.input = input;
        this.encrypt = encrypt;
    }

    abstract Chunk execute(IdeaKey key, int round);
}
