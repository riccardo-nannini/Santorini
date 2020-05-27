package it.polimi.ingsw.PSP13.view.GUI;

import javafx.event.Event;

import java.io.IOException;

/**
 * This interface is used to create a state pattern in order to give to the same view in godSelection.fxml a different
 * behaviour whether the game is in the GodSelection phase (the challenger is choosing the gods for this match)
 * or the GodInput phase (players are chosing their god for the match from the ones choosed previously by the challenger)
 */
public interface GodHandlerInterface {

    void initialization();

    void upload();

    void godClicked(Event event);

    void confirmClicked(Event event) throws IOException;

    void helperClicked(Event event) throws IOException;
}
