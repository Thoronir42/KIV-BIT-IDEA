package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.idea.codec.idea.IdeaCodec;

import java.io.*;

public class Main {

    private static String VERSION = "a0.1-test";

    private static void printHelp(String errMessage) {
        System.out.println(errMessage);
        System.out.println("Usage: cz.zcu.kiwi.idea <file-in> <file-out> <key>");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();
        if (args.length != 3) {
            printHelp("Invallid argument count");
        }

        main.run(args[0], args[1], args[2]);
    }

    private Main() {
    }

    private void run(String fileIn, String fileOut, String key) {
        IdeaCodec codec = new IdeaCodec(key);

        try {
            FileInputStream fis = new FileInputStream(new File(fileIn));
            FileOutputStream fos = new FileOutputStream(new File(fileOut));

            long encodedLength = codec.encode(fis, fos);
            System.out.println("Total encoded length: " + encodedLength);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
