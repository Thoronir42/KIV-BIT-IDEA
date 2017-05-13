package cz.zcu.kiwi.cryptography;

public class Key {

    protected final String text;
    protected final byte[] parts;

    /**
     * @param text Text containing hexadecimal characters representing the key
     * @param size byte count of key
     */
    public Key(String text, int size) {
        this.text = text;
        this.parts = parseToParts(text, size);
    }

    public byte part(int n) throws IndexOutOfBoundsException {
        return this.parts[n];
    }


    public String getText() {
        return text;
    }

    public int getSize() {
        return this.parts.length;
    }

    public String toString() {
        return super.toString() + " " + text;
    }



    private static byte[] parseToParts(String text, int size) {
        byte[] parts = new byte[size];

        int sourceLength = text.length();

        if (sourceLength < size * 2) {
            throw new IllegalArgumentException("Key must be at least " + size * 2 + "chars long. " + sourceLength + " given");
        }

        for (int i = 0; i < size; i++) {
            parts[i] = 0;
        }

        for (int i = 0, j = 0; i < sourceLength; i += 2, j = (j + 1) % size) {
            byte b1 = Byte.parseByte(text.substring(i, i + 1), 16);
            byte b2 = Byte.parseByte(text.substring(i + 1, i + 2), 16);
            parts[j] ^= (b1 << 4) | b2;
        }

        return parts;
    }

}
