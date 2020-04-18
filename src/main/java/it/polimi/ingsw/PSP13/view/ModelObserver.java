package it.polimi.ingsw.PSP13.view;

import it.polimi.ingsw.PSP13.immutables.BuilderVM;
import it.polimi.ingsw.PSP13.immutables.MapVM;

public class ModelObserver {

    private Input input;

    public ModelObserver(Input input) {
        this.input = input;
    }

    /**
     * Notifies view's class Input a change in model's map
     * @param mapVM Immutable map sent to the view
     */
    public void updateMap(MapVM mapVM) {
        input.updateMap(mapVM);
    }

    /**
     * Notifies view's class Input a change in model's builders
     * @param builderVM Immutables couple of builders sent to the view
     */
    public void updateBuilders(BuilderVM builderVM) {
        input.updateBuilders(builderVM);
    }

    /**
     * Notifies view's class Input that a player has won
     * @param username Name of the winner
     */
    public void notifyWin(String username) { input.notifyWin(username); }

}
