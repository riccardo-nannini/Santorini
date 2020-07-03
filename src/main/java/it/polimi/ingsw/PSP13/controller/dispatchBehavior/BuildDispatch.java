package it.polimi.ingsw.PSP13.controller.dispatchBehavior;

import it.polimi.ingsw.PSP13.controller.PermaLobby;
import it.polimi.ingsw.PSP13.controller.ViewObserver;
import it.polimi.ingsw.PSP13.network.client_callback.MessageFromViewToController;

public class BuildDispatch extends ServerDispatchBehavior {
    public BuildDispatch(PermaLobby lobby, ViewObserver viewObserver) {
        super(lobby, viewObserver);
    }

    /**
     * Performs the build iter. A user chose to build a building
     * @param messageVC the message to perform the dispatch. The specific behavior will be executed
     */
    @Override
    public void behavior(MessageFromViewToController messageVC) {
        if (messageVC.getCoords() != null) viewObserver.updateBuildInput(messageVC.getCoords());
    }
}
