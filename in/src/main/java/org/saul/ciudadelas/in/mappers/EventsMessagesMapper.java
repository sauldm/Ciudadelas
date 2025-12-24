package org.saul.ciudadelas.in.mappers;

import org.saul.ciudadelas.domain.game.EventMessage;
import org.saul.ciudadelas.in.dto.EventsMessagesDTO;

public class EventsMessagesMapper {
    public static EventsMessagesDTO toEventMessageDTO(EventMessage eventMessage){
        EventsMessagesDTO eventsMessagesDTO = new EventsMessagesDTO();
        eventsMessagesDTO.setId(eventMessage.getId());
        eventsMessagesDTO.setMessage(eventMessage.getMessage());
        eventsMessagesDTO.setEvents(eventMessage.getType().toString());
        return eventsMessagesDTO;
    }
}
