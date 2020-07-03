package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;
import it.polimi.ingsw.PSP13.controller.PermaLobby;

public class MoveDispatch extends ServerDispatchBehavior {
    public MoveDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    /**
     * Behavior related to the user move. The user chose to move a builder on a given cell
     * @param messageVC the message to perform the dispatch. The specific behavior will be executed
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getCoords() != null) viewObserver.updateMoveInput(messageVC.getCoords());
    }
}
