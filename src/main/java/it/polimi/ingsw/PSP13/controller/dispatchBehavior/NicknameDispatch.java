package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

import java.net.Socket;

public class NicknameDispatch extends ServerDispatchBehavior {

    private Socket socket;

    public NicknameDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    public NicknameDispatch(PermaLobby lobby, ViewObserver viewObserver, Socket socket) {
        super(lobby, viewObserver);
        this.socket = socket;
    }

    /**
     * behavior related to the nickname choice
     * @param messageVC
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getString() != null) lobby.validateNickname(socket,messageVC.getString());
    }
}
