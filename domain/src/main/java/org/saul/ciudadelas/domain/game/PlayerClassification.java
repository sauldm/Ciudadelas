package org.saul.ciudadelas.domain.game;

public class PlayerClassification {

    private final String nickName;
    private final int wins;

    public PlayerClassification(String nickName, Integer wins) {
        this.nickName = nickName;
        this.wins = wins;
    }

    public String getNickName() {
        return nickName;
    }

    public int getWins() {
        return wins;
    }

}
