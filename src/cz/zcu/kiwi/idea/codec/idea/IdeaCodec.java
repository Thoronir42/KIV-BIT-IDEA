package cz.zcu.kiwi.idea.codec.idea;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IdeaCodec {

    private final static int STEPS_COUNT = 8;

    private final Arithmetic arithmetic;
    private final int[] key;
    private final int[] reverseKey;


    public IdeaCodec(String key) {
        this.arithmetic = new Arithmetic(16, 16);
        this.key = this.parseKey(key);
        this.reverseKey = this.reverse(this.key);
    }

    private int[] parseKey(String key) {
        throw new NotImplementedException();
    }

    private int[] reverse(int[] key) {
        int[] result = new int[key.length];

        for (int i = 0; i < key.length; i++) {
            result[key.length - 1 - i] = key[i];
        }

        return result;
    }

    private int[] encodeBlock(int[] input, int[] key) {
        AIdeaStep[] steps = new AIdeaStep[STEPS_COUNT + 1];
        steps[0] = new IdeaStep(input, arithmetic);

        for (int i = 1; i < STEPS_COUNT; i++) {
            int[] stepResult = steps[i - 1].execute(key);
            steps[i] = new IdeaStep(stepResult, arithmetic);
        }
        steps[STEPS_COUNT] = new IdeaHalfStep(steps[STEPS_COUNT - 1].getOutput(), arithmetic);

        return steps[STEPS_COUNT].execute(key);
    }

    public long encode(InputStream input, OutputStream output) throws IOException {
        return this.processStream(input, output, this.key);
    }

    public long decode(InputStream input, OutputStream output) throws IOException {
        return this.processStream(input, output, this.reverseKey);
    }

    private long processStream(InputStream input, OutputStream output, int[] key) throws IOException {
        IdeaInputStream iis = new IdeaInputStream(input);

        long totalLength = 0;
        while (iis.hasMore()) {
            int[] encoded = encodeBlock(iis.nextBlock(), key);
            for (int anEncoded : encoded) {
                output.write(anEncoded);
            }

            totalLength += encoded.length;
        }

        return totalLength;
    }
}

