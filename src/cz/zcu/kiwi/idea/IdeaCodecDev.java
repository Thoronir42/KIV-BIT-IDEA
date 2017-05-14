package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.idea.data.Chunk;
import cz.zcu.kiwi.idea.data.IdeaInputStream;
import cz.zcu.kiwi.idea.data.IdeaOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IdeaCodecDev extends IdeaCodec {
    public IdeaCodecDev(IdeaKey key) {
        super(key);
    }

    public long encode(InputStream input, OutputStream output) throws IOException {
        IdeaInputStream iis = new IdeaInputStream(input);
        IdeaOutputStream ios = new IdeaOutputStream(output);

        long totalLength = 0;
        while (iis.hasMore()) {
            Chunk chunkIn = iis.nextChunk();
            Chunk chunkEncrypted = processBlock(chunkIn, this.key, true);
            Chunk chunkDecrypted = processBlock(chunkEncrypted, this.key, false);

            System.out.format("%s => %s => %s\n", chunkIn, chunkEncrypted, chunkDecrypted);

            ios.writeChunk(chunkDecrypted);
            totalLength += Chunk.SIZE;
        }

        return totalLength;
    }
}
