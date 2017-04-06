package cz.zcu.kiwi.scoring.codec.idea;

import cz.zcu.kiwi.scoring.codec.ICodec;

public class IdeaCodec implements ICodec {

    private final int STEPS_COUNT = 8;

    private final Arithmetic arithmetic;


    public IdeaCodec() {
        this.arithmetic = new Arithmetic(16, 16);
    }

    @Override
    public String encode(String plain) {

        return "";
    }

    @Override
    public String decode(String cipher) {
        return "";
    }

    private String encodeBlock(String plainBlock) {
        int[] input = {0x4263, 0x4899, 0x07F3, 0xCC89};
        int [] keys = {0x0D6F, 0x1EA2, 0x6086, 0XA7D1, 0x93B7, 0x77E2};

        AIdeaStep[] steps = new AIdeaStep[STEPS_COUNT + 1];
        steps[0] = new IdeaStep(input, arithmetic);

        for(int i = 1; i < STEPS_COUNT; i++) {
            int[] stepResult = steps[i - 1].execute(keys);
            steps[i] = new IdeaStep(stepResult, arithmetic);
        }
        steps[STEPS_COUNT] = new IdeaHalfStep(steps[STEPS_COUNT - 1].getOutput(), arithmetic);

        int[] output = steps[STEPS_COUNT].execute(keys);
        return "";
    }

    private String decodeBlock(String cipherBlock) {
        return "";
    }

    private int[] step;
}

