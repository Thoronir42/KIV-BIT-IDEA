package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.cryptography.Arithmetic;
import org.junit.Test;

import static org.junit.Assert.*;

public class IdeaStepsTest {

    @Test
    public void step() {
        Chunk input = new Chunk(0x01, 0x02, 0x03, 0x04);
        IdeaStep step = new IdeaStep(input, true);
        int[] expected = new int[] {
                Arithmetic.mult(1, 0x0102),
                Arithmetic.add(3, 0x0304),
                Arithmetic.add(2, 0x0506),
                Arithmetic.mult(4, 0x0708),
        };

        executeStep(step, expected);
    }

    @Test
    public void halfStep() {
        Chunk input = new Chunk(0x01, 0x02, 0x03, 0x04);
        IdeaHalfStep step = new IdeaHalfStep(input, true);
        int[] expected = new int[] {
                Arithmetic.mult(1, 0x0102),
                Arithmetic.add(3, 0x0304),
                Arithmetic.add(2, 0x0506),
                Arithmetic.mult(4, 0x0708),
        };

        executeStep(step, expected);
    }

    private void executeStep(AIdeaStep step, int[] expected) {
        IdeaKey key = IdeaKeyTest.mockKey();

        Chunk output = step.execute(key, 0);
//        assertArrayEquals(expected, output.getBlocks());
    }

}