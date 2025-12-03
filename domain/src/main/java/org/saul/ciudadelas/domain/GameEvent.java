package org.saul.ciudadelas.domain;

import org.saul.ciudadelas.domain.game.Events;
import org.saul.ciudadelas.domain.game.Game;

import java.util.List;

public class GameEvent {
    private Game game;
    private List<Events> events;

    public GameEvent(Game game, List<Events> events) {
        this.game = game;
        this.events = events;
    }

    public Game getGame() {
        return game;
    }

    public List<Events> getEvents() {
        return events;
    }
}
