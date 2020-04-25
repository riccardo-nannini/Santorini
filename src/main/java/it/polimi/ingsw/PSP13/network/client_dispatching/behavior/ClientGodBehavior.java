package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientGodBehavior extends ClientBuildBehavior {

    @Override
    public void behavior(MessageCV messageCV) {
        String player = messageCV.getString();
        List<String> chosenGods = messageCV.getStringList();
        boolean error = messageCV.isError();

        input.godInput(player, chosenGods, error);
    }

    public ClientGodBehavior(Input input)
    {
        super(input);
    }
}
