package org.saul.ciudadelas.domain.game.deck_cards;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.players.Player;

public interface OptionalEpicCard{

    void execute(Game game, Player player);
}
