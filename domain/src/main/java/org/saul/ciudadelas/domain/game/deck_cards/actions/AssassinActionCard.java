package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.RoundEvent;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;

public class AssassinActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {

    public AssassinActionCard(Long id) {
        super(id);
    }


    //ARREGLAR: El jugadro no puede elegirse a si mismo, ni con la carta de asesino ni con otra de su mazo
    @Override
    public void execute(Game game, CharacterCard characterCard) {
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        if (game.getActualRound().getActualTurn().getPlayer() == game.getPlayerByCharacter(characterCard)) throw new InternalGameException("El jugador no puede elegirse a si mismo");
        RoundEvent event = new RoundEvent(characterCard.getId(), () -> {
            game.stopCharacterPlaying(characterCard);
        });
        game.addRoundEvent(event);
    }
}
