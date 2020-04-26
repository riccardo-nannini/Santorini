package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientBuildBehavior extends ClientDispatcherBehavior{

    @Override
    public void behavior(MessageCV messageCV) {

        List<Coords> checkMoveCells = messageCV.getCoordsList();
        boolean error = messageCV.isError();

        input.buildInput(checkMoveCells, error);
    }

    public ClientBuildBehavior(Input input)
    {
        super(input);
    }


}
