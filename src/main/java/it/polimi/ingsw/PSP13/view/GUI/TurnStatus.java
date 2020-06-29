package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.model.player.Coords;

public abstract class TurnStatus {

    protected static GameMap map;

    /**
     * Handles a cell click calling the right observable to controller method
     * @param coords clicked cell coordinates
     */
    public abstract void selectCell(Coords coords);

    public static void setMap(GameMap map) {
        TurnStatus.map = map;
    }

}
