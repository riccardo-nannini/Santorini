package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientMoveBehavior extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {

        List<Coords> checkMoveCells = messageCV.getCoordsList();
        boolean error = messageCV.isError();

        input.moveInput(checkMoveCells, error);
    }

    public ClientMoveBehavior(Input input)
    {
        super(input);
    }
}
