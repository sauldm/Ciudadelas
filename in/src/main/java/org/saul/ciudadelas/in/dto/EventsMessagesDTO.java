package org.saul.ciudadelas.in.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@ToString
public class EventsMessagesDTO {

    UUID id;
    String events;
    String message;
}
