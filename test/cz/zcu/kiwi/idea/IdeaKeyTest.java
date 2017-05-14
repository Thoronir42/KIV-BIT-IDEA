package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.CryptoKey;
import cz.zcu.kiwi.generic.BoundsException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class IdeaKeyTest {

    private static byte[] createKey() {
        return new byte[]{
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
        };
    }

    private static CryptoKey createCryptoKey() {
        return new CryptoKey(createKey());
    }

    public static IdeaKey mockKey() {
        return new IdeaKey(createCryptoKey());
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
        for (int subKey : key) {
            assertTrue(0x0 <= subKey && subKey <= 0xFFFF);
        }
    }

}