package cz.zcu.kiwi.idea;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IdeaCodecDev extends IdeaCodec {
    public IdeaCodecDev(IdeaKey key) {
        super(key);
    }

    public long encode(InputStream input, OutputStream output) throws IOException {
        IdeaDataChannel channel = new IdeaDataChannel(input, output);

        long totalLength = 0;
        while (channel.hasMore()) {
            Chunk chunkIn = channel.readChunk();
            Chunk chunkEncrypted = processBlock(chunkIn, this.key, true);
            Chunk chunkDecrypted = processBlock(chunkEncrypted, this.key, false);

            System.out.format("%s => %s => %s\n", chunkIn, chunkEncrypted, chunkDecrypted);

            channel.writeChunk(chunkDecrypted);
            totalLength += Chunk.SIZE;
        }

        return totalLength;
    }
}
