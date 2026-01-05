package org.saul.ciudadelas.out.projection;

public record PlayerClassificationP(String nickName, Integer wins) {

    public PlayerClassificationP {
        if (wins == null) {
            wins = 0;
        }
    }
}
