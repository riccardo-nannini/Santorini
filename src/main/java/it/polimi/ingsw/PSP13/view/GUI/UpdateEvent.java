package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class UpdateEvent extends Event {


    public UpdateEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    public UpdateEvent(Object o, EventTarget eventTarget, EventType<? extends Event> eventType) {
        super(o, eventTarget, eventType);
    }
}
