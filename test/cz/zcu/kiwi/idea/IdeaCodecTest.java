package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.CryptoKey;
import cz.zcu.kiwi.cryptography.HexDecKey;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;


public class IdeaCodecTest {

    private IdeaCodec getCodec() {
        CryptoKey cKey = new HexDecKey("0102030405060708090A0B0C0D0E0F00", 16);
        IdeaKey key = new IdeaKey(cKey);
        return new IdeaCodec(key);
    }

    @Test
    public void testBlockSizeData() throws IOException{
        IdeaCodec codec = getCodec();

        byte[] plain = new byte[]{
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
        }, encoded, decoded;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(plain);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        assertEquals(16, codec.encode(inputStream, outputStream));
        encoded = outputStream.toByteArray();

        inputStream = new ByteArrayInputStream(encoded);
        outputStream = new ByteArrayOutputStream();

        assertEquals(16, codec.decode(inputStream, outputStream));
        decoded = outputStream.toByteArray();

        assertArrayEquals(plain, decoded);

    }

}