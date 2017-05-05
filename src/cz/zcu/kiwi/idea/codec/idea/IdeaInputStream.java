package cz.zcu.kiwi.idea.codec.idea;

import java.io.*;

public class IdeaInputStream implements AutoCloseable {

    private InputStream inputStream;

    public IdeaInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public IdeaInputStream(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    public int[] nextBlock() throws IOException {
        if (!hasMore()) {
            throw new IOException("End of stream reached");
        }
        return new int[]{
                readInt(),
                readInt(),
                readInt(),
                readInt()
        };
    }

    public boolean hasMore() throws IOException {
        return inputStream.available() > 0;
    }

    private int readInt() throws IOException {
        if (hasMore()) {
            return inputStream.read();
        }

        return 0;
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }
}
