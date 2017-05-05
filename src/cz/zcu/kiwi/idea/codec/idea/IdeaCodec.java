package cz.zcu.kiwi.idea.codec.idea;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class IdeaCodec {

    private final static int STEPS_COUNT = 8;

    private final Arithmetic arithmetic;
    private final int[] key;
    private final int[] reverseKey;


    public IdeaCodec(int[] key) throws IOException {
        this.arithmetic = new Arithmetic(16, 16);
        this.key = key;
        this.reverseKey = this.reverse(this.key);
    }

    private int[] reverse(int[] key) {
        int[] result = new int[key.length];

        for (int i = 0; i < key.length; i++) {
            result[key.length - 1 - i] = key[i];
        }

        return result;
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
            int[] blockIn = iis.nextBlock();
            System.out.println("In:\n" + Arrays.toString(blockIn));
            int[] blockOut = processBlock(blockIn, key);
            System.out.println("Out:\n" + Arrays.toString(blockIn));
            for (int n : blockOut) {
                output.write(n);
            }

            totalLength += blockOut.length;
        }

        return totalLength;
    }

    private int[] processBlock(int[] input, int[] key) {
        IdeaStep[] steps = new IdeaStep[STEPS_COUNT];
        steps[0] = new IdeaStep(input, arithmetic);

        for (int i = 1; i < STEPS_COUNT; i++) {
            steps[i] = steps[i - 1].nextStep(key);
        }

        IdeaHalfStep halfStep = steps[STEPS_COUNT - 1].nextHalfStep(key);

        return halfStep.execute(key);
    }
}

