package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientRmvBlockBehavior extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {
        List<Coords> removableBlocks = messageCV.getCoordsList();
        boolean error = messageCV.isError();

        input.removeBlock(removableBlocks, error);
    }

    public ClientRmvBlockBehavior(Input input)
    {
        super(input);
    }
}
