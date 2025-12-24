package org.saul.ciudadelas.domain.game;

import java.util.UUID;

public class EventMessage {
    private UUID id;
    private Events type;
    private String message;

    public EventMessage(Events type, String message) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.message = message;
    }

    public UUID getId(){
        return id;
    }
    public Events getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
