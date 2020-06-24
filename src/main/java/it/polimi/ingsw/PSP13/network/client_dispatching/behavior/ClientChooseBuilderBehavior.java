package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientChooseBuilderBehavior extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {

        String player = messageCV.getString();

        input.chooseBuilder(player);
    }

    public ClientChooseBuilderBehavior(Input input)
    {
        super(input);
    }
}
