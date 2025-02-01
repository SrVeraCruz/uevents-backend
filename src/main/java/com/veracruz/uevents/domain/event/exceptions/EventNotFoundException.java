package com.veracruz.uevents.domain.event.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("Event not Found");
    }
}
