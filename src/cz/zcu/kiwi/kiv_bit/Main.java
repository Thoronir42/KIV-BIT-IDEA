package cz.zcu.kiwi.kiv_bit;

import cz.zcu.kiwi.cryptography.HexDecKey;
import cz.zcu.kiwi.idea.IdeaCodec;
import cz.zcu.kiwi.idea.IdeaKey;

import java.io.*;

public class Main {

    private static final int DEBUG = 1;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();

        if (args.length != 4) {
            System.out.println("Invallid argument count");
            System.out.println("Usage: cz.zcu.kiwi.idea <operation> <file-in> <file-out> <key>");
        }

        try {

            String key = "123456789ABCDEF0123456789ABCDEF0";
            IdeaKey ideaKey = new IdeaKey(new HexDecKey(key, IdeaKey.SIZE));

            switch (DEBUG) {
                case 0:
                    main.run(args[0], args[1], args[2], args[3]);
                    break;
                case 1:
                    main.run("encode", "data/message.txt", "data/encrypted.txt", ideaKey);
                    main.run("decode", "data/encrypted.txt", "data/decrypted.txt", ideaKey);
                    break;
                    default:
                        throw new UnsupportedOperationException("Debug operation " + DEBUG + " not supported");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Main() {
    }

    private void run(String operation, String fileIn, String fileOut, String key) throws IOException {
        this.run(operation, fileIn, fileOut, new IdeaKey(new HexDecKey(key, IdeaKey.SIZE)));
    }

    private void run(String operation, String fileIn, String fileOut, IdeaKey key) throws IOException {

        IdeaCodec codec = new IdeaCodec(key);

        FileInputStream fis = new FileInputStream(new File(fileIn));
        FileOutputStream fos = new FileOutputStream(new File(fileOut));

        long totalLength;
        switch (operation) {
            case "encode":
                totalLength = codec.encode(fis, fos);
                break;
            case "decode":
                totalLength = codec.decode(fis, fos);
                break;
            default:
                throw new IllegalArgumentException("Operation " + operation + " not recognized");
        }

        System.out.format("Total %sd length: %d\n", operation, totalLength);
    }
}
