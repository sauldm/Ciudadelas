package org.saul.ciudadelas.domain.game.deck_cards.cards;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.MainDeckCardActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.WizardTarget;

public class CharacterCard extends Card{

    public CharacterCard(Long id, String name, Color color, boolean undestructible) {
        super(id, name, color, undestructible);
    }

    public void executeCharacterAbility(Game game, Long targetId) {
        if (this instanceof MainDeckCardActionCharacterCard && this instanceof OtherPlayerActionCharacterCard){
            WizardTarget target = WizardTarget.fromValue(targetId);
            System.out.println(target);
            switch (target){
                case GAMEDECK -> ((MainDeckCardActionCharacterCard) this).execute(game);
                case PLAYER -> ((OtherPlayerActionCharacterCard) this).execute(game, targetId);
            }
            return;
        }
        if (this instanceof OtherPlayerActionCharacterCard otherPlayerActionCharacterCard){
            otherPlayerActionCharacterCard.execute(game,targetId);
        }
    }

}
