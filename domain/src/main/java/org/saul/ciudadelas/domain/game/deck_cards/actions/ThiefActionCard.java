package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.RoundEvent;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class ThiefActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {
    public ThiefActionCard(Long id) {
        super(id);
    }

    @Override
    public void execute(Game game, CharacterCard characterCard) {
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        if (game.getActualRound().getActualTurn().getPlayer() == game.getPlayerByCharacter(characterCard)) throw new InternalGameException("El jugador no puede elegirse a si mismo");
        if (characterCard.getClass().equals(AssassinActionCard.class)) throw new ExpectedGameError("La carta no puede ser un asesino");
        RoundEvent event = new RoundEvent(characterCard.getId(), () -> {
            game.stoleCharacterGold(characterCard,this);
        });
        game.addRoundEvent(event);
    }
}
