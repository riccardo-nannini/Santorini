package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

public abstract class ClientDispatcherBehavior {

    protected Input input;

    public abstract void behavior(MessageFromControllerToView messageCV);

    public ClientDispatcherBehavior(Input input)
    {
        this.input = input;
    }

}
