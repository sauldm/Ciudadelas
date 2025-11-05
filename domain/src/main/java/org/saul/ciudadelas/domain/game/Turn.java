package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class Turn implements Comparable<Turn>{
    private Player player;
    private CharacterCard characterCard;
    private boolean canPlay = true;
    private boolean isPlaying = false;

    public Turn(Player player, CharacterCard characterCard){
        this.player = player;
        this.characterCard = characterCard;
    }

    public void startTurn(){
        if (!isPlaying) throw new InternalGameException("Este método solo puede activarse si el jugador esta jugando");
        if(canPlay){
            player.startPlaying(this,characterCard);
        }else player.turnSkipped(this,characterCard);
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
                ", canPlay=" + canPlay +
                ", isPlaying=" + isPlaying +
                '}';
    }
}
