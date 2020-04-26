package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientNickBehavior extends ClientDispatcherBehavior{


    @Override
    public void behavior(MessageCV messageCV) {
        input.nicknameInput(messageCV.isError());
    }

    public ClientNickBehavior(Input input)
    {
        super(input);
    }

}
