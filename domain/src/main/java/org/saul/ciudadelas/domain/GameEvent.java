package org.saul.ciudadelas.domain;

import org.saul.ciudadelas.domain.game.EventMessage;
import org.saul.ciudadelas.domain.game.Game;

import java.util.List;

public class GameEvent {
    private Game game;
    private List<EventMessage> events;

    public GameEvent(Game game, List<EventMessage> events) {
        this.game = game;
        this.events = events;
    }

    public Game getGame() {
        return game;
    }

    public List<EventMessage> getEvents() {
        return events;
    }
}
