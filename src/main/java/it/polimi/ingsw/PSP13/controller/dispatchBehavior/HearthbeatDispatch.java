package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

public class HearthbeatDispatch extends ServerDispatchBehavior {
    public HearthbeatDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    /**
     * Hearthbeat behavior. The server needs to know if the client is alive, if it is this message
     * is received and a timer is reset
     * @param messageVC the message to perform the dispatch. The specific behavior will be executed
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {

    }
}
