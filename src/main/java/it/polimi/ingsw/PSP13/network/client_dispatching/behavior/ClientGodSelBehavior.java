package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientGodSelBehavior extends ClientBuildBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {


        int godsNumber = messageCV.getGodsNumber();

        if (godsNumber != 0) {
            List<String> godsList = messageCV.getStringList();
            boolean error = messageCV.isError();
            input.godSelectionInput(godsList,godsNumber,error);
        } else {
            String challenger = messageCV.getString();
            input.printWaitGodsSelection(challenger);
        }


    }

    public ClientGodSelBehavior(Input input)
    {
        super(input);
    }
}
