package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.idea.data.Chunk;
import cz.zcu.kiwi.idea.data.IdeaInputStream;
import cz.zcu.kiwi.idea.data.IdeaOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IdeaCodec {

    final static int ROUNDS = 8;

    protected final IdeaKey key;


    public IdeaCodec(String key) {
        this(new IdeaKey(key));
    }

    public IdeaCodec(IdeaKey key) {
        this.key = key;
    }

    public long encode(InputStream input, OutputStream output) throws IOException {
        return this.processStream(input, output, true);
    }

    public long decode(InputStream input, OutputStream output) throws IOException {
        return this.processStream(input, output, false);
    }

    protected long processStream(InputStream input, OutputStream output, boolean encrypt) throws IOException {
        IdeaInputStream iis = new IdeaInputStream(input);
        IdeaOutputStream ios = new IdeaOutputStream(output);

        long totalLength = 0;
        while (iis.hasMore()) {
            Chunk chunkIn = iis.nextChunk();
            Chunk chunkOut = processBlock(chunkIn, this.key, encrypt);
            ios.writeChunk(chunkOut);

            totalLength += Chunk.SIZE;
        }

        return totalLength;
    }

    protected Chunk processBlock(Chunk input, IdeaKey key, boolean encrypt) {
        IdeaStep[] steps = new IdeaStep[ROUNDS];
        steps[0] = new IdeaStep(input, encrypt);

        for (int i = 1; i < ROUNDS; i++) {
            steps[i] = steps[i - 1].nextStep(key, i - 1);
        }

        IdeaHalfStep halfStep = steps[ROUNDS - 1].nextHalfStep(key, ROUNDS - 1);

        return halfStep.execute(key, ROUNDS);
    }
}

