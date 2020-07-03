package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;
import it.polimi.ingsw.PSP13.controller.PermaLobby;

public abstract class ServerDispatchBehavior {

    protected PermaLobby lobby;
    protected ViewObserver viewObserver;

    public ServerDispatchBehavior(PermaLobby lobby, ViewObserver viewObserver) {
        this.lobby = lobby;
        this.viewObserver = viewObserver;
    }

    /**
     * executes a set of instructions based on the message protocol
     * @param messageVC the message to perform the dispatch. The specific behavior will be executed
     */
    public abstract void behavior(MessageFromViewToController messageVC);

}
