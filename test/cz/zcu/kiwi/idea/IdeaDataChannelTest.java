package cz.zcu.kiwi.idea;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class IdeaDataChannelTest {

    private IdeaDataChannel getInputChannel() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{0x01, 0x02, 0x03, 0x04});
        return new IdeaDataChannel(inputStream, null);
    }

    @Test
    public void readChunk() throws Exception {
        IdeaDataChannel channel = getInputChannel();

        assertTrue(channel.hasMore());

        Chunk chunk = channel.readChunk();
        int[] expectedBlocks = new int[]{0x0102, 0x0304, 0x0000, 0x0000};
        assertArrayEquals(expectedBlocks, chunk.getBlocks());

        assertFalse(channel.hasMore());

        channel.close();
    }

    @Test(expected = IOException.class)
    public void readChunkException() throws Exception {
        IdeaDataChannel channel = getInputChannel();

        channel.readChunk();
        assertFalse(channel.hasMore());
        channel.readChunk();

        channel.close();
    }

    @Test
    public void writeChunk() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        IdeaDataChannel channel = new IdeaDataChannel(null, outputStream);

        channel.writeChunk(new Chunk(0x0102, 0x0304, 0x0506, 0x0708));

        byte[] expected = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
        assertArrayEquals(expected, outputStream.toByteArray());

        channel.close();
    }

}