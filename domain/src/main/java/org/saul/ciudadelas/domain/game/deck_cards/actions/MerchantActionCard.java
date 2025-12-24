package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.EventMessage;
import org.saul.ciudadelas.domain.game.Events;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.StartTurnActionCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class MerchantActionCard extends CharacterCard implements StartTurnActionCard {
    public MerchantActionCard() {
        super(6L,"Merchant", Color.GREEN,false,"Merch",1,0L);
    }

    @Override
    public void execute(Game game, Player player) {
        if (player == null) throw new InternalGameException("El player no puede ser null");
        player.addGold(1L);
        game.getEventsBuffer().add(new EventMessage(Events.MESSAGE, "El jugador consigue 1 moneda"));
    }
}
