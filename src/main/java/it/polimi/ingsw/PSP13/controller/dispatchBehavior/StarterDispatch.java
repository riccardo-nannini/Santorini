package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

public class StarterDispatch extends ServerDispatchBehavior {
    public StarterDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    /**
     * Behavior related the choice of the starting player
     * @param messageVC the message to perform the dispatch. the specific behavior will be executed
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getString() != null) viewObserver.updateStarter(messageVC.getString());

    }
}
