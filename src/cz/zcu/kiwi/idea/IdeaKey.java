package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Key;
import cz.zcu.kiwi.generic.BoundsException;

final class IdeaKey extends Key {

    private static final int SIZE = 16;

    private final boolean inverse;

    private final int[] subKeys;

    IdeaKey(String text) {
        this(text, false);
    }

    private IdeaKey(String text, boolean inverse) {
        super(text, SIZE);

        this.inverse = inverse;
        this.subKeys = generateSubkeys(this.parts);
    }

    private IdeaKey(String text, boolean inverse, int[] subKeys) {
        super(text, SIZE);

        this.inverse = inverse;
        this.subKeys = subKeys;
    }

    int subKey(int n, int round) {
        if(0 > round || round > 9) {
            throw new BoundsException(0, IdeaCodec.ROUNDS + 1, round);
        }
        if(round == 9 && n > 4) {
            throw new IndexOutOfBoundsException("Last (half-)round subkey index must be less than 4");
        }
        return this.subKeys[n];
    }

    IdeaKey inverse() {
        byte[] inverted = new byte[this.subKeys.length];
        for(int i = 0; i < this.subKeys.length; i++) {
            inverted[i] = this.subKeys[this.subKeys.length - 1 - i];
        }

        return new IdeaKey(this.text, !this.inverse, inverted);
    }

    private static byte[] generateSubKeys(byte[] parts, boolean inverse) {
        // todo: fix it, bitch
        return parts;
    }
}
