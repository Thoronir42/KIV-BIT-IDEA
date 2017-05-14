package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;
import cz.zcu.kiwi.cryptography.Key;
import cz.zcu.kiwi.generic.BoundsException;

public final class IdeaKey extends Key {

    private static final int SIZE = 16; // 16 bytes = 128-bit key
    private static final int SUBKEYS_IN_ROUND = 6;

    private final boolean inverse;

    private final int[] subKeys;

    IdeaKey(String text) {
        this(text, false);
    }

    private IdeaKey(String text, boolean inverse) {
        super(text, SIZE);

        this.inverse = inverse;
        this.subKeys = generateSubkeys(this.parts);

        System.out.println(text);

        for(int i = 0; i < SUBKEYS_IN_ROUND; i++) {
            System.out.format("%16d", i);
        }
        System.out.println();

        for (int i = 0; i < this.subKeys.length; i++) {
//            String block = String.format("%16s", Integer.toBinaryString(this.subKeys[i])).replace(' ', '0');
            String block = String.format("%04x", this.subKeys[i]);
            System.out.print(block);
            if((i % SUBKEYS_IN_ROUND) == 5) {
                System.out.println();
            }
        }

        System.out.println();System.out.println();
    }

    private IdeaKey(String text, boolean inverse, int[] subKeys) {
        super(text, SIZE);

        this.inverse = inverse;
        this.subKeys = subKeys;
    }

    int subKey(int n, int round) {
        if (0 > round || round > 9) {
            throw new BoundsException(0, IdeaCodec.ROUNDS + 1, round);
        }
        if (round == 9 && n > 4) {
            throw new IndexOutOfBoundsException("Last (half-)round subKey index must be less than 4");
        }
        return this.subKeys[round * SUBKEYS_IN_ROUND + n];
    }

    IdeaKey inverse() {
        int[] invertedSubKeys = invertSubKeys(this.subKeys);

        return new IdeaKey(this.text, !this.inverse, invertedSubKeys);
    }

    /**
     * Creating the subKeys from the user key.
     *
     * @param userKey 128-bit user key
     * @return 52 16-bit key sub-blocks (six for each of the eight rounds and four more for the output transformation)
     */
    private static int[] generateSubkeys(byte[] userKey) {
        if (userKey.length != SIZE) {
            throw new IllegalArgumentException();
        }
        int[] subKeys = new int[IdeaCodec.ROUNDS * 6 + 4]; // 52 16-bit subKeys

        // The 128-bit userKey is divided into eight 16-bit subKeys
        int b1, b2;
        for (int i = 0; i < userKey.length; i += 2) {
            subKeys[i] = Arithmetic.concatBytes(userKey[i], userKey[i + 1]);
        }

        // The key is rotated 25 bits to the left and again divided into eight subKeys.
        // The first four are used in round 2; the last four are used in round 3.
        // The key is rotated another 25 bits to the left for the next eight subKeys, and so on.
        for (int i = userKey.length / 2; i < subKeys.length; i++) {
            // It starts combining k1 shifted 9 bits with k2. This is 16 bits of k0 + 9 bits shifted from k1 = 25 bits
            b1 = subKeys[(i + 1) % 8 != 0 ? i - 7 : i - 15] << 9;   // k1,k2,k3...k6,k7,k0,k9, k10...k14,k15,k8,k17,k18...
            b2 = subKeys[(i + 2) % 8 < 2 ? i - 14 : i - 6] >>> 7;   // k2,k3,k4...k7,k0,k1,k10,k11...k15,k8, k9,k18,k19...
            subKeys[i] = (b1 | b2) & 0xFFFF;
        }
        return subKeys;
    }

    /**
     * Reverse and invert the subKeys to get the decryption subKeys.
     * They are either the additive or multiplicative inverses of the encryption subKeys in reverse order.
     *
     * @param subKey subKeys
     * @return inverted key
     */
    private static int[] invertSubKeys(int[] subKey) {
        int[] invSubKeys = new int[subKey.length];
        int p = 0;
        int i = IdeaCodec.ROUNDS * 6;
        // For the final output transformation (round 9)
        invSubKeys[i] = Arithmetic.mulInv(subKey[p++]);     // 48 <- 0
        invSubKeys[i + 1] = Arithmetic.inv(subKey[p++]);     // 49 <- 1
        invSubKeys[i + 2] = Arithmetic.inv(subKey[p++]);     // 50 <- 2
        invSubKeys[i + 3] = Arithmetic.mulInv(subKey[p++]);     // 51 <- 3
        // From round 8 to 2
        for (int r = IdeaCodec.ROUNDS - 1; r > 0; r--) {
            i = r * 6;
            invSubKeys[i + 4] = subKey[p++];         // 46 <- 4 ...
            invSubKeys[i + 5] = subKey[p++];         // 47 <- 5 ...
            invSubKeys[i] = Arithmetic.mulInv(subKey[p++]); // 42 <- 6 ...
            invSubKeys[i + 2] = Arithmetic.inv(subKey[p++]); // 44 <- 7 ...
            invSubKeys[i + 1] = Arithmetic.inv(subKey[p++]); // 43 <- 8 ...
            invSubKeys[i + 3] = Arithmetic.mulInv(subKey[p++]); // 45 <- 9 ...
        }
        // Round 1
        invSubKeys[4] = subKey[p++];                 // 4 <- 46
        invSubKeys[5] = subKey[p++];                 // 5 <- 47
        invSubKeys[0] = Arithmetic.mulInv(subKey[p++]);         // 0 <- 48
        invSubKeys[1] = Arithmetic.inv(subKey[p++]);         // 1 <- 49
        invSubKeys[2] = Arithmetic.inv(subKey[p++]);         // 2 <- 50
        invSubKeys[3] = Arithmetic.mulInv(subKey[p]);           // 3 <- 51
        return invSubKeys;
    }
}
