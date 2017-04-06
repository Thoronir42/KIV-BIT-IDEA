package cz.zcu.kiwi.scoring.codec.idea;

public class IdeaHalfStep extends AIdeaStep {

    IdeaHalfStep(int[] input, Arithmetic a) {
        super(input, a);
    }

    @Override
    int[] execute(int[] keys) {
        return output = new int[]{
                a.mult(input[0], keys[0]),
                a.add(input[1], keys[1]),
                a.add(input[2], keys[2]),
                a.mult(input[3], keys[3])
        };
    }
}
