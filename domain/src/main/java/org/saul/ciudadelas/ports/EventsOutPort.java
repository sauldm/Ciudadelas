package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.GameEvent;

public interface EventsOutPort {
    void publishEvent(GameEvent gameEvent);
}
