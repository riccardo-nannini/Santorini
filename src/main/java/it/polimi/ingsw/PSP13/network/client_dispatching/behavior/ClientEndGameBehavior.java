package it.polimi.ingsw.PSP13.network.client_dispatching.behavior;

import it.polimi.ingsw.PSP13.network.client_dispatching.MessageCV;
import it.polimi.ingsw.PSP13.view.Input;

public class ClientEndGameBehavior extends ClientDispatcherBehavior{

    @Override
    public void behavior(MessageCV messageCV) {

        String endgame = messageCV.getString();

        if (endgame.equals("Win")) {
            input.notifyWin();
        } else if (endgame.equals("Lose")) {
            input.notifyLose();
        }
    }

    public ClientEndGameBehavior(Input input)
    {
        super(input);
    }


}
