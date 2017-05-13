package cz.zcu.kiwi.idea.data;

import java.io.IOException;
import java.io.OutputStream;

public class IdeaOutputStream implements AutoCloseable {
    private final OutputStream outputStream;

    public IdeaOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeChunk(Chunk chunk) throws IOException {
        for (int block : chunk.getBlocks()) {
            writeBlock(block);
        }
    }

    void writeBlock(int block) throws IOException {
        outputStream.write(block >> 8);
        outputStream.write(block & 0xFF);
    }

    @Override
    public void close() throws Exception {
        outputStream.close();
    }
}
