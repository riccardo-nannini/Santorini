package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientEffectBehavior extends ClientBuildBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {
        String god = messageCV.getString();

        input.effectInput(god);
    }

    public ClientEffectBehavior(Input input)
    {
        super(input);
    }
}
