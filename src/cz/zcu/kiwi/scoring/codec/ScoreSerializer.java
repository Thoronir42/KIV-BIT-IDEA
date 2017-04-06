package cz.zcu.kiwi.scoring.codec;

import cz.zcu.kiwi.scoring.ScoreEntry;

/**
 * @author Skoro
 */
public class ScoreSerializer {

    private String separator = "|";

    public String serialize(ScoreEntry entry) {
        return entry.getVersion() + separator + entry.getName() + separator + entry.getScore();
    }

    public ScoreEntry deserialize(String text) {
        String[] parts = text.split(separator);
        if(parts.length != 3) {
            throw new IllegalArgumentException(text + " is not a valid ScoreEntry representation");
        }

        try{
            String version = parts[0];
            String name = parts[1];
            int score = Integer.parseInt(parts[2]);

            return new ScoreEntry(name, score, version);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(parts[2] + " is not a valid score value");
        }
    }

}
