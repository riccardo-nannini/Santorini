package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageFromControllerToView;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientEndGameBehavior extends ClientDispatcherBehavior{

    @Override
    public void behavior(MessageFromControllerToView messageCV) {

        String endgame = messageCV.getString();

        if (endgame.equals("Win")) {
            input.notifyWin();
        } else if (endgame.equals("Lose")) {
            input.notifyLose();
        }

        input.playAgain();

    }

    public ClientEndGameBehavior(Input input)
    {
        super(input);
    }


}
