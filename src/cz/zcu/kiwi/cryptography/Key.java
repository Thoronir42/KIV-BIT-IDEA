package cz.zcu.kiwi.cryptography;

public class Key {

    protected final String text;
    protected final byte[] parts;

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

        int keyLength = text.length();

        if (keyLength < size) {
            throw new IllegalArgumentException("Key must be at least " + size + "chars long. " + keyLength + " given");
        }

        for (int i = 0; i < size; i++) {
            parts[i] = 0;
        }
        for (int i = 0, j = 0; i < keyLength; i++, j = (j + 1) % size) {
            parts[j] ^= (byte) text.charAt(i);
        }

        return parts;
    }

}
