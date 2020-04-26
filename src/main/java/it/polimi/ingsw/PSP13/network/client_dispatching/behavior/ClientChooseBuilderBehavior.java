package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientChooseBuilderBehavior extends ClientEffectBehavior {

    @Override
    public void behavior(MessageCV messageCV) {

        String player = messageCV.getString();

        input.chooseBuilder(player);
    }

    public ClientChooseBuilderBehavior(Input input)
    {
        super(input);
    }
}
