package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.network.client_dispatching.UpdateListener;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientDisconnectionBehavior extends ClientDispatcherBehavior {

    private final UpdateListener updateListener;

    @Override
    public void behavior(MessageFromControllerToView messageCV) {
        input.disconnectionMessage();
        updateListener.setExit(true);
    }

    public ClientDisconnectionBehavior(Input input, UpdateListener updateListener)
    {
        super(input);
        this.updateListener = updateListener;

    }
}
