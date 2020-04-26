package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.model.player.Coords;
import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientRmvBlockBehavior extends ClientChooseBuilderBehavior {

    @Override
    public void behavior(MessageCV messageCV) {
        List<Coords> removableBlocks = messageCV.getCoordsList();
        boolean error = messageCV.isError();

        input.removeBlock(removableBlocks, error);
    }

    public ClientRmvBlockBehavior(Input input)
    {
        super(input);
    }
}
