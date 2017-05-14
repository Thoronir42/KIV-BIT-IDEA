package cz.zcu.kiwi.cryptography;

import org.junit.Test;

import static org.junit.Assert.*;

public class KeyTest {

    @Test
    public void testPart() {
        Key k = new Key("0110", 2);

        assertEquals(0x01, k.part(0));
        assertEquals(0x10, k.part(1));
    }

    @Test
    public void testLongText() {
        Key k = new Key("011010", 2);

        assertEquals(0x11, k.part(0));
        assertEquals(0x10, k.part(1));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testShortTextException() {
        new Key("01", 2);
    }
}