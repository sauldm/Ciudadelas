package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.EventMessage;
import org.saul.ciudadelas.domain.game.Events;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.MainDeckCardActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class WizardActionCard extends CharacterCard implements MainDeckCardActionCharacterCard, OtherPlayerActionCharacterCard {


    public WizardActionCard() {
        super(3L,"Ilusionista", Color.GREY,false,"Elige entre intercambiar cartas con\n El mazo\n El jugador",1,0L);
    }

    @Override
    public void execute(Game game) {
        game.swapCardsWithGame(this);

    }


    @Override
    public void execute(Game game, Long targetPlayerId) {
        if (targetPlayerId == null) throw new InternalGameException("El id del jugador objetivo no puede ser nulo");
        game.swapHandsWithPlayer(this, targetPlayerId);

    }
}
