package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.EventMessage;
import org.saul.ciudadelas.domain.game.Events;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.StartTurnActionCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;

public class ArchitectActionCard extends CharacterCard implements StartTurnActionCard {
    public ArchitectActionCard() {
        super(7L,"Architect", Color.GREY,false, "arc",2,0L);
    }

    @Override
    public void execute(Game game, Player player) {
        if (player == null) throw new InternalGameException("El jugador no puede ser null");
        if (game.districtCards().size() == 0){
            game.getEventsBuffer().add(new EventMessage(Events.MESSAGE, "No quedan cartas en el mazo"));
            return;
        }
        List<DistrictCard> districtCardsGained = game.getDistrictCards(2);
        player.addDistrictCardsInHand(districtCardsGained);
        player.setPrivateDistrictGained(districtCardsGained);
        game.getEventsBuffer().add(new EventMessage(Events.ARQUITECT,"Cartas obtenidas por el arquitecto"));
    }
}
