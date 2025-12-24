package org.saul.ciudadelas.domain.game.deck_cards.cards;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.*;
import org.saul.ciudadelas.domain.game.players.Player;

public class CharacterCard extends Card{

    private int maxDistrictsToBuild;

    public CharacterCard(Long id, String name, Color color, boolean undestructible, String description, int maxDistrictsToBuild, Long price) {
        super(id, name, color, undestructible,description,price);
        this.maxDistrictsToBuild = maxDistrictsToBuild;
    }

    public void executeCharacterAbility(Game game, Long targetId, Player player) {
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
        if (this instanceof StartTurnActionCard startTurnActionCard){
            startTurnActionCard.execute(game, player);
        }
    }

    public boolean canBuildDistrict(int districtsBuiltThisTurn) {
        return districtsBuiltThisTurn < maxDistrictsToBuild;
    }
}
