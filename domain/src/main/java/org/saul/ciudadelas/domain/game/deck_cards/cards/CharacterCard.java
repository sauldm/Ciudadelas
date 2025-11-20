package org.saul.ciudadelas.domain.game.deck_cards.cards;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.MainDeckCardActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.WizardTarget;

public class CharacterCard extends Card{

    private int maxDistrictsToBuild;

    public CharacterCard(Long id, String name, Color color, boolean undestructible, int maxDistrictsToBuild) {
        super(id, name, color, undestructible);
        this.maxDistrictsToBuild = maxDistrictsToBuild;
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

    public boolean canBuildDistrict(int districtsBuiltThisTurn) {
        return districtsBuiltThisTurn < maxDistrictsToBuild;
    }
}
