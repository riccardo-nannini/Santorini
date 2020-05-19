package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;


public class ClientEffectDescriptionBehaviour extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {

        String effect = messageCV.getString();
        List<String> godEffects = messageCV.getStringList();

        input.setEffectDescription(effect, godEffects);
    }

    public ClientEffectDescriptionBehaviour(Input input)
    {
        super(input);
    }

}
