package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientPlayerNumBehavior extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageCV messageCV) {

        input.choosePlayerNum(messageCV.isError());
    }

    public ClientPlayerNumBehavior(Input input)
    {
        super(input);
    }
}
