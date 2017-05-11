package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Key;
import cz.zcu.kiwi.generic.BoundsException;

public class IdeaKey extends Key {

    private static final int SIZE = 6;

    private final boolean inverse;

    private final byte[] subKeys;

    IdeaKey(String text) {
        this(text, false);
    }

    IdeaKey(String text, boolean inverse) {
        super(text, SIZE);

        this.inverse = inverse;
        this.subKeys = generateSubKeys(this.parts, inverse);
    }

    private IdeaKey(String text, boolean inverse, byte[] subKeys) {
        super(text, SIZE);

        this.inverse = inverse;
        this.subKeys = subKeys;
    }

    public int subKey(int n, int round) {
        if(0 > round || round > 8) {
            throw new BoundsException(0, IdeaCodec.ROUND_COUNT, round);
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
