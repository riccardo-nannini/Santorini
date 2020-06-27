package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientWaitQueueBehavior extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {
        input.waitQueueMsg();
    }

    public ClientWaitQueueBehavior(Input input)
    {
        super(input);
    }
}
