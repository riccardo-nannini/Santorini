package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientGodSelBehavior extends ClientBuildBehavior {

    @Override
    public void behavior(MessageCV messageCV) {

        String challenger = messageCV.getString();
        List<String> godsList = messageCV.getStringList();
        int godsNumber = messageCV.getGodsNumber();
        boolean error = messageCV.isError();

        input.godSelectionInput(challenger,godsList,godsNumber,error);
    }

    public ClientGodSelBehavior(Input input)
    {
        super(input);
    }
}
