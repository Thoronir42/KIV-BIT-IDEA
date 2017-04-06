package cz.zcu.kiwi.scoring;

public class Main {

    private static void printHelp(String errMessage){
        System.out.println(errMessage);
        System.out.println("Usage: cz.zcu.kiwi.scoring <name> <score>");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 2){
            printHelp("Invallid argument count");
        }
    }
}
