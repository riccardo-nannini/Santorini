package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

public class RemoveBlockDispatch extends ServerDispatchBehavior {
    public RemoveBlockDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    /**
     * behavior related to the remove of a building on the map
     * @param messageVC
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getCoords() != null) viewObserver.updateRemoveInput(messageVC.getCoords());

    }
}
