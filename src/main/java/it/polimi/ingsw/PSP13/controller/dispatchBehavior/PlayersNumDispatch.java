package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

public class PlayersNumDispatch extends ServerDispatchBehavior {
    public PlayersNumDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    /**
     * Behavior related to the first player choosing the player number
     * @param messageVC the message to perform the dispatch. The specific behavior will be executed
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getPlayerNum() != 0) lobby.validatePlayerNumber(messageVC.getPlayerNum());

    }
}
