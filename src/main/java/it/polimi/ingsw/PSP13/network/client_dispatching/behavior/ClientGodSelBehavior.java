package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientGodSelBehavior extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {

        int godsNumber = messageCV.getGodsNumber();
        List<String> godsList = messageCV.getStringList();

        if (godsNumber != 0) {
            boolean error = messageCV.isError();
            input.godSelectionInput(godsList,godsNumber,error);
        } else {
            String challenger = messageCV.getString();
            input.printWaitGodsSelection(challenger, godsList);
        }


    }

    public ClientGodSelBehavior(Input input)
    {
        super(input);
    }
}
