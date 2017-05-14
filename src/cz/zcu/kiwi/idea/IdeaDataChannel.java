package cz.zcu.kiwi.idea;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class IdeaDataChannel implements AutoCloseable{
    private final InputStream inputStream;
    private final OutputStream outputStream;

    IdeaDataChannel(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public Chunk readChunk() throws IOException {
        if (!hasMore()) {
            throw new IOException("End of stream reached");
        }

        return new Chunk(readPart(), readPart(), readPart(), readPart());
    }

    public boolean hasMore() throws IOException {
        return inputStream.available() > 0;
    }

    private int readPart() throws IOException {
        int block = 0;

        if (hasMore()) {
            block = inputStream.read() << 8;
        }
        if (hasMore()) {
            block |= inputStream.read();
        }

        return block;
    }

    public void writeChunk(Chunk chunk) throws IOException {
        for (int block : chunk.getBlocks()) {
            writeBlock(block);
        }
    }

    private void writeBlock(int block) throws IOException {
        outputStream.write(block >> 8);
        outputStream.write(block & 0xFF);
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }
}
