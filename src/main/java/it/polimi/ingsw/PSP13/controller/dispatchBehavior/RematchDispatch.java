package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

import java.net.Socket;

public class RematchDispatch extends ServerDispatchBehavior {

    private Socket socket;

    public RematchDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    public RematchDispatch(PermaLobby lobby, ViewObserver viewObserver, Socket socket) {
        super(lobby, viewObserver);
        this.socket = socket;
    }

    /**
     * Behavior related to the rematch iter
     * @param messageVC the message to perform the dispatch. The specific behavior will be executed
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getString() != null) lobby.fillPlayAgainMap(socket, messageVC.getString());

    }
}
