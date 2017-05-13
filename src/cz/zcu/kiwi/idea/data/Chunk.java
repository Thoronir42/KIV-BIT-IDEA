package cz.zcu.kiwi.idea.data;

public class Chunk {

    public static final int SIZE = 8; // 4 blocks * 2 bytes
    private int[] blocks;

    public Chunk(int... blocks) {
        if (blocks.length != 4) {
            throw new IllegalArgumentException("Block count in chunk must be 4");
        }

        this.blocks = blocks;
    }

    int[] getBlocks() {
        return this.blocks;
    }

    public int getBlock(int n) throws IndexOutOfBoundsException {
        return this.blocks[n];
    }

    public String toString() {
        return String.format("[%04x, %04x, %04x, %04x]", blocks[0], blocks[1], blocks[2], blocks[3]);
    }
}
