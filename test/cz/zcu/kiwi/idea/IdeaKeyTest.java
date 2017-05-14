package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.CryptoKey;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class IdeaKeyTest {

    private static CryptoKey createKey() {
        int[] src = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        return new CryptoKey(src);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shortKeyException() {
        new IdeaKey(new CryptoKey(new int[2]));
    }

    @Test
    public void generateEncryptionKey() throws Exception {
        int[] subKeys = IdeaKey.generateEncryptionKey(createKey().getParts());
        assertKeySize(subKeys);
    }

    @Test
    public void createDecryptionKey() throws Exception {
        int[] key = IdeaKey.generateEncryptionKey(createKey().getParts());

        int[] keyInverted = IdeaKey.createDecryptionKey(key);
        assertKeySize(keyInverted);

        int[] keyT = IdeaKey.createDecryptionKey(keyInverted);

        assertArrayEquals(key, keyT);
    }

    private void assertKeySize(int[] key) {
        assertEquals(52, key.length);
        for(int subKey : key) {
            assertTrue(0x0 <= subKey && subKey <= 0xFFFF);
        }
    }

}