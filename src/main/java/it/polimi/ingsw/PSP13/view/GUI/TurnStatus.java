package it.polimi.ingsw.PSP13.view.GUI;

import it.polimi.ingsw.PSP13.model.player.Coords;

public abstract class TurnStatus {

    protected static Mappa map;

    public abstract void selectCell(Coords coords);

    public static void setMap(Mappa map) {
        TurnStatus.map = map;
    }

}
