package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientStarterBehaviour extends ClientBuildBehavior {


    @Override
    public void behavior(MessageCV messageCV) {
        if (messageCV.getString() == null) {
            boolean error = messageCV.isError();
            List<String> usernames = messageCV.getStringList();
            input.starterInput(error, usernames);
        } else {
            if (!messageCV.isError()) input.printWaitStarterSelection(messageCV.getString());
        }
    }



    public ClientStarterBehaviour(Input input)
    {
        super(input);
    }
}
