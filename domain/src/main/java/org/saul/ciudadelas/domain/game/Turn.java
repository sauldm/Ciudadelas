package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class Turn implements Comparable<Turn>{
    private final Player player;
    private final CharacterCard characterCard;
    private boolean canPlay = true;
    private boolean isPlaying = false;
    private boolean isCompleted = false;
    private boolean isCharacterHabilityUsed = false;

    public Turn(Player player, CharacterCard characterCard){
        this.player = player;
        this.characterCard = characterCard;
    }

    public boolean canPlayerPlay(){
        return canPlay;
    }

    public boolean isCharacterHabilityUsed(){
        return isCharacterHabilityUsed;
    }

    public void characterHabilityUsed(){
        isCharacterHabilityUsed = true;
    }

    public Long getCharacterId(){
        return characterCard.getId();
    }

    public boolean isTurnCompleted(){
        return isCompleted;
    }

    public void startTurn(){
        isPlaying = true;
    }

    public void stopPlaying(){
        isCompleted = true;
        canPlay = false;
    }

    public void endTurn(){
        isCompleted = true;
        isPlaying = false;
    }


    @Override
    public int compareTo(Turn o) {
        return this.characterCard.compareTo(o.characterCard);
    }

    @Override
    public String toString() {
        return "Turn{" +
                "player=" + player +
                ", characterCard=" + characterCard +
                ", isPlaying=" + isPlaying +
                '}';
    }

    public Player getPlayer() {
        return player;
    }

    public CharacterCard getCharacter() {
        return characterCard;
    }
}
