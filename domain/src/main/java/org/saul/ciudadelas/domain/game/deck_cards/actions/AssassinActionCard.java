package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.EventMessage;
import org.saul.ciudadelas.domain.game.Events;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.RoundEvent;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;

public class AssassinActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {

    public AssassinActionCard() {
        super(1L,"Assassin", Color.GREY, false,"Assa",1,0L);
    }


    @Override
    public void execute(Game game, Long characterCardId) {
        if (characterCardId == null) throw new InternalGameException("La carta no puede ser nula");
        if (game.getActualRound().getActualTurn().getPlayer() == game.findPlayerByCharacterId(characterCardId)){
            game.getEventsBuffer().add(new EventMessage(Events.MESSAGE,"El jugador no puede elegirse a si mismo"));
            return;
        }
        RoundEvent event = new RoundEvent(characterCardId, (actualGame) -> {
            actualGame.stopCharacterPlaying(characterCardId);
        });
        game.getActualRound().getActualTurn().characterHabilityUsed();
        game.addTurnSkipped(characterCardId);
        game.addRoundEvent(event);
        if (game.getActualRound().getTurnByCharacter(characterCardId) == null){
            game.getEventsBuffer().add(new EventMessage(Events.CHARACTER_HABILITY_USED,"Ha usado la habilidad de "+game.findCharacterCardById(this.getId()).getName()));
            return;
        }
        game.getActualRound().skipCharacterTurn(characterCardId);
        game.getEventsBuffer().add(new EventMessage(Events.CHARACTER_HABILITY_USED,"Ha usado la habilidad de "+game.findCharacterCardById(this.getId()).getName()));

    }
}
