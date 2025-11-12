package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.MainDeckCardActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class WizardActionCard extends CharacterCard implements MainDeckCardActionCharacterCard {
    public WizardActionCard(Long id) {
        super(id);
    }

    @Override
    public void execute(Game game, Player player) {
        if (player == game.getActualRound().getActualTurn().getPlayer()) throw new InternalGameException("El jugador no puede elegirse a si mismo");
        if (player == null) throw new InternalGameException("El jugador no puede ser nulo");
        game.swapHandsWithPlayer(player,this);
    }
}
