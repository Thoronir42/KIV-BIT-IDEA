package cz.zcu.kiwi.generic;

public class BoundsException extends IndexOutOfBoundsException {
    public BoundsException(int min, int max, int n) {
        super(String.format("Index must be in interval <%d, %d>, but was %d", min, max, n));

    }

}
