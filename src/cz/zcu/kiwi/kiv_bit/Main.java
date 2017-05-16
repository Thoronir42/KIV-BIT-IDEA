package cz.zcu.kiwi.kiv_bit;

import cz.zcu.kiwi.commands.CommandUsageException;

public class Main {


    public static void main(String[] args) {
        try {
            IdeaFileCommand command = new IdeaFileCommand(args);
            if(!command.execute()) {
                System.exit(2);
            }
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            if(ex instanceof CommandUsageException) {
                System.out.println(((CommandUsageException) ex).getUsage());
            }
            System.exit(1);
        }
    }
}
