package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class IdeaCodec {

    final static int ROUND_COUNT = 8;
    static final int HALF_ROUND = 9;

    private final Arithmetic arithmetic;
    private final IdeaKey key;


    public IdeaCodec(String key) {
        this.arithmetic = new Arithmetic();
        this.key = new IdeaKey(key);
    }

    public long encode(InputStream input, OutputStream output) throws IOException {
        return this.processStream(input, output, this.key);
    }

    public long decode(InputStream input, OutputStream output) throws IOException {
        return this.processStream(input, output, this.key.inverse());
    }

    private long processStream(InputStream input, OutputStream output, IdeaKey key) throws IOException {
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

    private int[] processBlock(int[] input, IdeaKey key) {
        IdeaStep[] steps = new IdeaStep[ROUND_COUNT];
        steps[0] = new IdeaStep(input, arithmetic);

        for (int i = 1; i < ROUND_COUNT; i++) {
            steps[i] = steps[i - 1].nextStep(key, i - 1);
        }

        IdeaHalfStep halfStep = steps[ROUND_COUNT - 1].nextHalfStep(key, ROUND_COUNT - 1);

        return halfStep.execute(key, HALF_ROUND);
    }
}

