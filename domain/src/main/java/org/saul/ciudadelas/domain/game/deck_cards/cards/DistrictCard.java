package org.saul.ciudadelas.domain.game.deck_cards.cards;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.OptionalEpicCard;
import org.saul.ciudadelas.domain.game.deck_cards.StartTurnActionCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class DistrictCard extends Card{
    private int points;


    public DistrictCard(Long id, String name, Color color,boolean undestructible, String description,Long price, int points) {
        super(id, name, color, undestructible,description, price);
        this.points = points;
    }

    public Long getPrice() {
        return super.getPrice();
    }
    public int getPoints(){
        return points;
    }

    public void executeDistrictAbility(Game game, Player player) {
        if (this instanceof OptionalEpicCard optionalEpicCard){
            optionalEpicCard.execute(game,player);
        }
        if (this instanceof StartTurnActionCard startTurnActionCard){
            startTurnActionCard.execute(game,player);
        }
    }
}
