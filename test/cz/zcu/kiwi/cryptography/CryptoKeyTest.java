package cz.zcu.kiwi.cryptography;

import org.junit.Test;

import static org.junit.Assert.*;

public class CryptoKeyTest {
    @Test
    public void toStringTest() throws Exception {
        CryptoKey key = new CryptoKey(new byte[] {1, 2});

        assertEquals("[01 02]", key.toString());
    }

    @Test
    public void getParts() throws Exception {
        byte[] parts = new byte[]{1, 2, 3};
        CryptoKey key = new CryptoKey(parts);

        assertArrayEquals(parts, key.getParts());
    }

}