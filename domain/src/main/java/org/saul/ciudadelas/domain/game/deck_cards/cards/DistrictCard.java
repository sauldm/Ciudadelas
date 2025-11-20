package org.saul.ciudadelas.domain.game.deck_cards.cards;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.OptionalEpicCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class DistrictCard extends Card{

    private Long price;
    private int points;


    public DistrictCard(Long id, String name, Color color,boolean undestructible, Long price, int points) {
        super(id, name, color, undestructible);
        this.price = price;
        this.points = points;
    }

    public Long getPrice() {
        return price;
    }

    public void executeDistrictAbility(Game game, Player player) {
        if (this instanceof OptionalEpicCard optionalEpicCard){
            optionalEpicCard.execute(game,player);
        }
    }
}
