package cz.zcu.kiwi.scoring;


/**
 * @author Skoro
 */
public class ScoreEntry {

    private final String version;
    private final String name;
    private final int score;

    public ScoreEntry(String name, int score, String version) {
        this.name = name;
        this.score = score;
        this.version = version;
    }

    public ScoreEntry(String name, String score, String version) {
        this(name, Integer.parseInt(score), version);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getVersion() {
        return version;
    }
}
