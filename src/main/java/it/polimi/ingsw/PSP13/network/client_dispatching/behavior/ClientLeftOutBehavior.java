package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientLeftOutBehavior extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageCV messageCV) {

        System.out.println("YOU GOT LEFT OUT");
    }

    public ClientLeftOutBehavior(Input input)
    {
        super(input);
    }
}
