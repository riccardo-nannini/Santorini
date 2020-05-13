package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientWaitQueueBehavior extends ClientDispatcherBehavior{

    @Override
    public void behavior(MessageFromControllerToView messageCV) {

        System.out.println("Players limit has been reached for this match, you can wait in queue or disconnect. your priority is hold.");
    }

    public ClientWaitQueueBehavior(Input input)
    {
        super(input);
    }
}
