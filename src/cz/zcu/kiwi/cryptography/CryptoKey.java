package cz.zcu.kiwi.cryptography;

public abstract class CryptoKey {
    protected final byte[] parts;

    public CryptoKey(byte[] parts) {
        this.parts = parts;
    }

    public byte part(int n) throws IndexOutOfBoundsException {
        return this.parts[n];
    }

    public int getSize() {
        return this.parts.length;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            byte p = parts[i];
            sb.append(String.format("%02X", p));
            if(i != parts.length - 1) {
                sb.append(", ");
            }
        }
        return "[" + sb.toString() + "]";
    }

    public byte[] getParts() {
        return parts;
    }
}
