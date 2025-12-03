package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.GameEvent;

public interface EventsInPort {
    void publishEvent(GameEvent gameEvent);
}
