package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.CryptoKey;
import cz.zcu.kiwi.generic.BoundsException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class IdeaKeyTest {

    private static byte[] createKey() {
        return new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    }

    private static CryptoKey createCryptoKey() {
        return new CryptoKey(createKey());
    }


    @Test(expected = IllegalArgumentException.class)
    public void shortKeyException() {
        new IdeaKey(new CryptoKey(new byte[2]));
    }

    @Test
    public void generateEncryptionKey() throws Exception {
        int[] subKeys = IdeaKey.generateEncryptionKey(createKey());
        assertKeySize(subKeys);
    }

    @Test
    public void createDecryptionKey() throws Exception {
        int[] key = IdeaKey.generateEncryptionKey(createKey());

        int[] keyInverted = IdeaKey.createDecryptionKey(key);
        assertKeySize(keyInverted);

        int[] keyT = IdeaKey.createDecryptionKey(keyInverted);

        assertArrayEquals(key, keyT);
    }

    @Test
    public void subKey() {
        IdeaKey key = new IdeaKey(createCryptoKey());

        assertEquals(0x0102, key.subKey(0, 0, true));
        assertNotEquals(0x0102, key.subKey(0, 0, false));
    }

    @Test(expected = BoundsException.class)
    public void subKeyRoundException() {
        IdeaKey key = new IdeaKey(createCryptoKey());

        key.subKey(0, 9, true);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void subKeyNException() {
        IdeaKey key = new IdeaKey(createCryptoKey());

        key.subKey(6, 0, true);
    }

    private void assertKeySize(int[] key) {
        assertEquals(52, key.length);
        for(int subKey : key) {
            assertTrue(0x0 <= subKey && subKey <= 0xFFFF);
        }
    }

}