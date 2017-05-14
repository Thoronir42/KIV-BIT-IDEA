package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.CryptoKey;
import cz.zcu.kiwi.cryptography.Arithmetic;
import cz.zcu.kiwi.generic.BoundsException;

public final class IdeaKey {

    public static final int SIZE = 16; // 16 bytes = 128-bit key
    private static final int SUB_KEYS_IN_ROUND = 6;
    private static final int SUB_KEYS = IdeaCodec.ROUNDS * SUB_KEYS_IN_ROUND + 4;

    private final int[] encryptionKey;
    private final int[] decryptionKey;

    public IdeaKey(CryptoKey key) {
        if(key.getSize() != SIZE) {
            throw new IllegalArgumentException();
        }

        this.encryptionKey = generateEncryptionKey(key.getParts());
        this.decryptionKey = createDecryptionKey(this.encryptionKey);

        printKey(this.encryptionKey);
        printKey(this.decryptionKey);
    }

    private void printKey(int[] key) {
        for (int i = 0; i < SUB_KEYS_IN_ROUND; i++) {
            System.out.format("%5d", i);
        }
        System.out.println();

        for (int i = 0; i < key.length; i++) {
//            String block = String.format("%16s", Integer.toBinaryString(this.encryptionKey[i])).replace(' ', '0');
            String block = String.format("%04x ", key[i]);
            System.out.print(block);
            if ((i % SUB_KEYS_IN_ROUND) == 5) {
                System.out.println();
            }
        }

        System.out.println();
        System.out.println();
    }

    int subKey(int n, int round, boolean encrypt) {
        if (0 > round || round > 9) {
            throw new BoundsException(0, IdeaCodec.ROUNDS + 1, round);
        }
        if (round == 9 && n > 4) {
            throw new IndexOutOfBoundsException("Last (half-)round subKey index must be less than 4");
        }

        if (encrypt) {
            return this.encryptionKey[round * SUB_KEYS_IN_ROUND + n];
        } else {
            return this.decryptionKey[round * SUB_KEYS_IN_ROUND + n];
        }
    }

    private static int[] generateEncryptionKey(byte[] key) {
        int[] ek = new int[SUB_KEYS]; // encryption key 52 16-bit encryptionKey
        //First, the 128-bit key is partitioned into eight 16-bit sub-blocks
        for (int i = 0; i < 8; i++) {
            ek[i] = Arithmetic.concatBytes(key[i * 2], key[i * 2 + 1]);
        }

        //Expand encryption subKeys
        for (int i = 8; i < SUB_KEYS; i++) {
            if ((i % 8) == 6)
                ek[i] = (ek[i - 7] << 9) | (ek[i - 14] >> 7);
            else if ((i % 8) == 7)
                ek[i] = (ek[i - 15] << 9) | (ek[i - 14] >> 7);
            else
                ek[i] = (ek[i - 7] << 9) | (ek[i - 6] >> 7);

            ek[i] &= 0xFFFF;
        }

        return ek;
    }

    private static int[] createDecryptionKey(int[] ek) {
        int[] dk = new int[SUB_KEYS];

        //Generate subKeys for decryption
        for (int i = 0; i < SUB_KEYS; i += 6) {
            dk[i] = Arithmetic.modularInverse(ek[48 - i]);

            if (i == 0 || i == 48) {
                dk[i + 1] = Arithmetic.addInv(ek[49 - i]);
                dk[i + 2] = Arithmetic.addInv(ek[50 - i]);
            } else {
                dk[i + 1] = Arithmetic.addInv(ek[50 - i]);
                dk[i + 2] = Arithmetic.addInv(ek[49 - i]);
            }

            dk[i + 3] = Arithmetic.modularInverse(ek[51 - i]);

            if (i < 48) {
                dk[i + 4] = ek[46 - i];
                dk[i + 5] = ek[47 - i];
            }
        }

        return dk;
    }
}
