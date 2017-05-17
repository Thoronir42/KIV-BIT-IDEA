package cz.zcu.kiwi.cryptography;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlockCipherModeTest {
    @Test
    public void validParsing() {
        assertEquals(BlockCipherMode.ECB, BlockCipherMode.valueOf("ECB"));
        assertEquals(BlockCipherMode.CBC, BlockCipherMode.valueOf("CBC"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invallidParsing() {
        BlockCipherMode.valueOf("WHOOPS");
    }
}