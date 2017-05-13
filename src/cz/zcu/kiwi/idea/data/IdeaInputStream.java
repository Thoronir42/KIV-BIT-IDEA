package cz.zcu.kiwi.idea.data;


import java.io.IOException;
import java.io.InputStream;

public class IdeaInputStream implements AutoCloseable {

    private InputStream inputStream;

    public IdeaInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Chunk nextChunk() throws IOException {
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

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }
}
