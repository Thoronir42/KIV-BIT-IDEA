package cz.zcu.kiwi.kiv_bit;

import cz.zcu.kiwi.idea.IdeaCodec;

import java.io.*;

public class Main {

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
//            main.run(args[0], args[1], args[2], args[3]);
            String key = "12345678901234567890123456789012";

            main.run("decode", "data/message.txt", "data/encrypted.txt", key);
            main.run("decode", "data/encrypted.txt", "data/decrypted.txt", key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Main() {
    }

    private void run(String operation, String fileIn, String fileOut, String key) throws IOException {

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
