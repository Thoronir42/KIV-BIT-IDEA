package cz.zcu.kiwi.kiv_bit;

import cz.zcu.kiwi.commands.CommandUsageException;
import cz.zcu.kiwi.commands.ICommand;
import cz.zcu.kiwi.cryptography.BlockCipherMode;
import cz.zcu.kiwi.cryptography.HexDecKey;
import cz.zcu.kiwi.idea.IdeaCodec;
import cz.zcu.kiwi.idea.IdeaKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class IdeaFileCommand implements ICommand {

    private static final String USAGE = "cz.zcu.kiwi.idea operation key file-in file-out [--mode mode] [-s]\n" +
            "    operation  encode/decode\n" +
            "    key  at least 32 HexDec characters\n" +
            "    file-in  input file to be processed\n" +
            "    file-out  output file\n\n" +
            "    --mode  parameter specifies block cipher mode\n" +
            "            currently supported modes are: " + Arrays.toString(BlockCipherMode.values()) + "\n\n" +
            "    -s  silent mode disables any output";

    private String operation;
    private IdeaKey key;
    private String fileIn, fileOut;

    private boolean silent = false;

    private BlockCipherMode mode = BlockCipherMode.ECB;

    IdeaFileCommand(String[] args) throws CommandUsageException {
        if (args == null || args.length < 4) {
            throw new CommandUsageException("Not enough arguments", USAGE);
        }
        this.operation = this.validateOperation(args[0]);
        this.key = new IdeaKey(new HexDecKey(args[1], IdeaKey.SIZE));
        this.fileIn = args[2];
        this.fileOut = args[3];
        for (int i = 4; i < args.length; i++) {
            switch (args[i]) {
                case "--mode":
                    this.mode = this.parseMode(args, ++i);
                    break;
                case "-s":
                    this.silent = true;
                    break;
            }
        }

    }

    private BlockCipherMode parseMode(String[] args, int i) throws IllegalArgumentException {
        if (args.length < i + 1) {
            throw new CommandUsageException("Not enough arguments", USAGE);
        }
        String mode = args[i].toUpperCase();
        try {
            return BlockCipherMode.valueOf(mode);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Block cipher mode " + mode + " not supported");
        }
    }

    private String validateOperation(String operation) {
        switch (operation) {
            case "encode":
            case "decode":
                return operation;

            default:
                throw new CommandUsageException("Operation " + operation + " not recognized", USAGE);
        }
    }

    public boolean execute() {
        IdeaCodec codec = new IdeaCodec(this.key);
        try (FileInputStream fis = new FileInputStream(new File(this.fileIn));
             FileOutputStream fos = new FileOutputStream(new File(this.fileOut))) {

            long totalLength = -1;
            long time = System.currentTimeMillis();
            switch (operation) {
                case "encode":
                    totalLength = codec.encode(fis, fos);
                    break;
                case "decode":
                    totalLength = codec.decode(fis, fos);
                    break;
            }
            time = System.currentTimeMillis() - time;

            if (!this.silent) {
                System.out.format("Total %sd length: %s\n", operation, humanReadableByteCount(totalLength, false));
                System.out.format("Execution time: %.3f s\n", time / 1000.f);
            }


        } catch (IOException ex) {
            if (!this.silent) {
                System.err.println(ex.toString());
            }
            return false;
        }

        return true;
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

}
