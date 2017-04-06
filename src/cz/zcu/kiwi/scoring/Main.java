package cz.zcu.kiwi.scoring;

import cz.zcu.kiwi.scoring.codec.ScoreSerializer;

public class Main {

    private static String VERSION = "a0.1-test";

    private static void printHelp(String errMessage){
        System.out.println(errMessage);
        System.out.println("Usage: cz.zcu.kiwi.scoring <name> <score>");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();

        main.run(args);
    }

    private final ScoreSerializer serializer;

    private Main() {
        serializer = new ScoreSerializer();
    }

    private void run(String[] args) {
        if(args.length != 2){
            printHelp("Invallid argument count");
        }

        ScoreEntry entry = new ScoreEntry(args[0], args[1], VERSION);

        System.out.println(serializer.serialize(entry));
    }
}
