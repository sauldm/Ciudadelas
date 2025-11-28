package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.RoundEvent;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;

public class AssassinActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {

    public AssassinActionCard() {
        super(1L,"Assassin", Color.GREY, false,1);
    }


    @Override
    public void execute(Game game, Long characterCardId) {
        if (characterCardId == null) throw new InternalGameException("La carta no puede ser nula");
        if (game.getActualRound().getActualTurn().getPlayer() == game.findPlayerByCharacterId(characterCardId)) throw new InternalGameException("El jugador no puede elegirse a si mismo");
        RoundEvent event = new RoundEvent(characterCardId, (actualGame) -> {
            actualGame.stopCharacterPlaying(characterCardId);
        });
        game.getActualRound().getActualTurn().characterHabilityUsed();
        game.addTurnSkipped(characterCardId);
        game.addRoundEvent(event);
    }
}
