package it.polimi.ingsw.PSP13.view.GUI.status;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.view.GUI.TurnStatus;

public class SetupStatus extends TurnStatus {
    @Override
    public void selectCell(Coords coords) {
        map.getGuiInput().getController().notifySetupBuilder(coords);
    }
}
