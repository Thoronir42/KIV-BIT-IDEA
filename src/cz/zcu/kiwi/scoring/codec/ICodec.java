package cz.zcu.kiwi.scoring.codec;

public interface ICodec {
    String encode(String plain);

    String decode(String cipher);
}
