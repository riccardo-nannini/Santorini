package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

import java.util.List;

public class ClientGodBehavior extends ClientDispatcherBehavior {

    @Override
    public void behavior(MessageFromControllerToView messageCV) {

        List<String> chosenGods = messageCV.getStringList();

        if (chosenGods.size()==1) {
            input.printAssignedGod(chosenGods.get(0));
        } else if (messageCV.getGodsNumber() == 1) {
            boolean error = messageCV.isError();
            input.godInput(chosenGods, error);
        } else {
            input.printWaitGodSelection(messageCV.getString(), chosenGods);
        }
    }

    public ClientGodBehavior(Input input)
    {
        super(input);
    }
}
