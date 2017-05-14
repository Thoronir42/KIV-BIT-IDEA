package cz.zcu.kiwi.idea;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChunkTest {

    @Test(expected = IllegalArgumentException.class)
    public void invalidLength() {
        new Chunk();
    }

    @Test
    public void getBlock() {
        Chunk chunk = new Chunk(1, 2, 3, 4);

        int[] ints = new int[]{1, 2, 3, 4};
        for (int i = 0; i < ints.length; i++) {
            assertEquals(ints[i], chunk.getBlock(i));
        }

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getBlockException() {
        Chunk chunk = new Chunk(1, 2, 3, 4);
        chunk.getBlock(4);
    }


    @Test
    public void toStringTest() {
        Chunk chunk = new Chunk(1, 2, 3, 4);

        String expected = "[0001, 0002, 0003, 0004]";
        assertEquals(expected, chunk.toString());
    }

}