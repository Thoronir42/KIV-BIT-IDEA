package cz.zcu.kiwi.commands;

public class CommandUsageException extends IllegalArgumentException{

    private final String usage;

    public CommandUsageException(String message, String usage) {
        super(message);
        this.usage = usage;
    }

    public String getUsage() {
        return usage;
    }
}
