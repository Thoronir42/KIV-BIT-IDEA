package cz.zcu.kiwi.idea;

import cz.zcu.kiwi.idea.codec.idea.IdeaCodec;
import generic.SimpleScanner;
import generic.SimpleScannerException;

import java.io.*;

public class Main {

    private static void printHelp(String errMessage) {
        System.out.println(errMessage);
        System.out.println("Usage: cz.zcu.kiwi.idea <operation> <file-in> <file-out> <key>");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();
        if (args.length != 4) {
            printHelp("Invallid argument count");
        }

        try {
            main.run(args[0], args[1], args[2], args[3]);
        } catch (IOException | SimpleScannerException e) {
            e.printStackTrace();
        }
    }

    private Main() {
    }

    private void run(String operation, String fileIn, String fileOut, String key) throws IOException, SimpleScannerException {

        IdeaCodec codec = new IdeaCodec(this.parseKey(key));

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

        System.out.println("Total processed length: " + totalLength);

    }

    private int[] parseKey(String key) throws IOException, SimpleScannerException {
        SimpleScanner sc = new SimpleScanner(SimpleScanner.RADIX_HEXADECIMAL, key);

        return new int[]{
                sc.nextShort(),
                sc.nextShort(),
                sc.nextShort(),
                sc.nextShort(),
                sc.nextShort(),
                sc.nextShort(),
        };
    }
}
