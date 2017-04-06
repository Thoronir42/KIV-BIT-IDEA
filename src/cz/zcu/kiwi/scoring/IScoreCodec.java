package cz.zcu.kiwi.scoring;

/**
 *
 * @author Skoro
 */
public interface IScoreCodec {
	
	public String encode(ScoreEntry entry);
	
	public ScoreEntry decode(String cipher);
	
}
