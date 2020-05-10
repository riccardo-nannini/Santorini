package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientWaitQueueBehavior extends ClientDispatcherBehavior{

    @Override
    public void behavior(MessageCV messageCV) {

        System.out.println("Players limiit has been reached for this match, you can wait in queue or disconnect. your priority is hold.");
    }

    public ClientWaitQueueBehavior(Input input)
    {
        super(input);
    }
}
