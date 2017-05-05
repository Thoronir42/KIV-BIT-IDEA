package cz.zcu.kiwi.idea.codec.idea;

public class IdeaHalfStep extends AIdeaStep {

    IdeaHalfStep(int[] input, Arithmetic a) {
        super(input, a);
    }

    @Override
    int[] execute(int[] key) {
        return output = new int[]{
                a.mult(input[0], key[0]),
                a.add(input[1], key[1]),
                a.add(input[2], key[2]),
                a.mult(input[3], key[3])
        };
    }
}
