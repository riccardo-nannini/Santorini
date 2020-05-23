package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;

import java.io.IOException;

public interface GodHandlerInterface {

    void initialization();

    void upload();

    void godClicked(Event event);

    void confirmClicked(Event event);

    void helperClicked(Event event) throws IOException;
}
