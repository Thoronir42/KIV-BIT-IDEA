package cz.zcu.kiwi.cryptography;

import org.junit.Test;

import static org.junit.Assert.*;

public class HexDecKeyTest {

    @Test
    public void testPart() {
        HexDecKey k = new HexDecKey("0110", 2);

        assertEquals(0x01, k.part(0));
        assertEquals(0x10, k.part(1));
    }

    @Test
    public void testLongText() {
        HexDecKey k = new HexDecKey("011010", 2);

        assertEquals(0x11, k.part(0));
        assertEquals(0x10, k.part(1));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testShortTextException() {
        new HexDecKey("01", 2);
    }
}